package com.creditsuise.coding.test.consumer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.creditsuise.coding.test.CreditSuiseFileBasedLogAnalyzerApplication;

@Component
public class AlertConsumerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditSuiseFileBasedLogAnalyzerApplication.class);

	@Autowired
	private TaskExecutor alertConsumerExecutor;

	@Autowired
	private ApplicationContext appCtx;
	private Boolean debug = true;

	@PostConstruct
	public void onStartUp() {
		AlertConsumer alertConsumer = appCtx.getBean(AlertConsumer.class);
		alertConsumerExecutor.execute(alertConsumer);
		if (debug) {
			LOGGER.info("AlertConsumer: '{}' has been started.", alertConsumer);
		}
	}
	
	public void onAlertConsumerTaskCompletion() {
		if(alertConsumerExecutor instanceof ThreadPoolTaskExecutor ) {
			ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor)alertConsumerExecutor;
			threadPoolTaskExecutor.setAwaitTerminationSeconds(10);
			threadPoolTaskExecutor.shutdown();
		}
	}	
}
