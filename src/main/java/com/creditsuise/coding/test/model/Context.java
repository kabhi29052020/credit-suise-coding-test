package com.creditsuise.coding.test.model;

import java.util.HashMap;
import java.util.Map;

import com.creditsuise.coding.test.model.persistence.Alert;

public class Context {
	private String logFilePath;
	private Map<String, Alert> alertsMap = new HashMap<>();

	private Context() {
	}

	public static Context newInstance() {
		return new Context();
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public Map<String, Alert> getAlertsMap() {
		return alertsMap;
	}

	public void setAlertsMap(Map<String, Alert> alertsMap) {
		this.alertsMap = alertsMap;
	}

}
