package com.creditsuise.coding.test.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.creditsuise.coding.test.model.persistence.Alert;

public final class AlertBlockingQueue {
	private final static BlockingQueue<Alert> blockingQueue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
	
	private AlertBlockingQueue() {
		if (blockingQueue != null) {
			throw new RuntimeException("Use only getFixedThreadPoolExecutor() to get instance of FixedThreadPoolExecutor!");
		}
	}
	
	public static BlockingQueue<Alert> getInstance() {
		return blockingQueue;
	}
}
