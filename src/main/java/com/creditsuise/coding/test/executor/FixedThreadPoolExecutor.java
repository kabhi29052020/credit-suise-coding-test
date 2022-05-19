package com.creditsuise.coding.test.executor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class FixedThreadPoolExecutor {

	private static final ThreadFactory customThreadfactory = new ThreadFactoryBuilder()
			.setNamePrefix("log-analyzer-thread").setDaemon(false).setPriority(Thread.NORM_PRIORITY)
			.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.err.println(String.format("Thread %s threw exception - %s", t.getName(), e.getMessage()));
				}
			}).build();

	private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), customThreadfactory);
	
	private FixedThreadPoolExecutor() {
		if (executorService != null) {
			throw new RuntimeException("Use only getFixedThreadPoolExecutor() to get instance of FixedThreadPoolExecutor!");
		}
	}
	
	public static ExecutorService getInstance() {
		return executorService;
	}
	
	public static void shutdown() {
		System.out.println("Performing some shutdown cleanup...");
		if(!executorService.isShutdown()) {
			executorService.shutdown();
			while (true) {
	            try {
	                System.out.println("Waiting for the service to terminate...");
	                if (executorService.awaitTermination(60, TimeUnit.SECONDS)) {
	                    break;
	                }
	            } catch (InterruptedException e) {
	            	executorService.shutdownNow();
	            }
	        }
		}
	}
}
