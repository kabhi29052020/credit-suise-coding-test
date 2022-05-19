package com.creditsuise.coding.test.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.creditsuise.coding.test.model.Event;
import com.creditsuise.coding.test.task.EventMap;

@Configuration
public class Beans {
	@Bean
	@Scope("prototype")
	public EventMap eventMap() {
		return new EventMap(new ConcurrentHashMap<String, Event>());
	}
	
	@Bean("alertConsumerExecutor")
	public ThreadPoolTaskExecutor lruTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("Alert Consumer - ");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;		
	}
}
