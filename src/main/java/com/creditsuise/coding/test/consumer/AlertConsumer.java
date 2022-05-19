package com.creditsuise.coding.test.consumer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.creditsuise.coding.test.config.LogConfiguration;
import com.creditsuise.coding.test.manager.LogAnalyserManager;
import com.creditsuise.coding.test.model.persistence.Alert;
import com.creditsuise.coding.test.queue.AlertBlockingQueue;
import com.creditsuise.coding.test.service.AlertPersistenceService;

@Component
@Scope("application")
public class AlertConsumer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyserManager.class);

	@Autowired
	private AlertPersistenceService alertPersistenceService;

	@SuppressWarnings("unused")
	@Autowired
	private LogConfiguration logConfiguration;

	public void run() {
		LOGGER.info("AlertConsumer started at {}", new Date());
		while (true) {
			try {
				Alert alert = AlertBlockingQueue.getInstance().poll(1, TimeUnit.SECONDS);
				if (alert != null) {
					LOGGER.info("**** Processing alert: {} ****", alert);
					persistAlert(alert);
				}
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
		}
	}

	private void persistAlert(Alert alert) {
		alertPersistenceService.persistAlert(alert);
	}
}
