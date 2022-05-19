package com.creditsuise.coding.test.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditsuise.coding.test.config.LogConfiguration;
import com.creditsuise.coding.test.model.persistence.Alert;
import com.creditsuise.coding.test.repository.AlertRepository;

@Service
public class AlertPersistenceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertPersistenceService.class);

	@Autowired
	private AlertRepository alertRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private LogConfiguration logConfiguration;
	
	public void persistAlert(Alert alert) {
		/**
		 * Alerts map holds the events that are parsed before persisting in a DB table.
		 * Each alert would have its execution time calculated and flagged (isAlert TRUE or FALSE).
		 */
		Map<String, Alert> alerts = alert.getContext().getAlertsMap();
		
		// to reduce memory consumption, write off the alerts once the pool has enough alerts
		// batch operation
//		if (alerts.size() < logConfiguration.getTableRowsWriteoffCount()) {
//			alerts.put(alert.getId(), alert);
//			return;
//		}
		alerts.put(alert.getId(), alert);
		persistAlerts(alerts.values());
		alerts.clear();
	}

	public void persistAlerts(Collection<Alert> alerts) {
		LOGGER.info("**** Persisting {} alerts. Alerts: {} ****", alerts.size(), alerts);
		alertRepository.saveAll(alerts);
	}
}
