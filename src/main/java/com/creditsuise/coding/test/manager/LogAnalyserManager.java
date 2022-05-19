package com.creditsuise.coding.test.manager;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.creditsuise.coding.test.config.LogConfiguration;
import com.creditsuise.coding.test.executor.FixedThreadPoolExecutor;
import com.creditsuise.coding.test.model.Context;
import com.creditsuise.coding.test.model.persistence.Alert;
import com.creditsuise.coding.test.task.EventMap;
import com.creditsuise.coding.test.task.EventTask;
import com.creditsuise.coding.test.utilities.FileUtility;

@Component
public class LogAnalyserManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyserManager.class);
	
	@Autowired
	private LogConfiguration applicationData;
	
	@Autowired
	private ApplicationContext appCtx;

	public void processEvents(Context context) throws IOException {
		LOGGER.info("Started processing events. It may take some time.");
		Throwable suppressedException = null;
		EventMap eventMap = appCtx.getBean(EventMap.class);		
		FileUtility fileUtil = new FileUtility(context);
		LineIterator lineIterator = null;
		context.setAlertsMap(new HashMap<String, Alert>());
		try {
			lineIterator = fileUtil.newLineIterator();
			while (lineIterator.hasNext()) {
				// process event
				String line = lineIterator.nextLine();;
				LOGGER.info("Submitting event task: {}", line);
				EventTask eventTask = new EventTask(eventMap, applicationData, line, context);
				FixedThreadPoolExecutor.getInstance().submit(eventTask);
			}
		} catch (IOException e) {
			suppressedException = e;
		} finally {
			try {
				lineIterator.close();
			} catch (IOException e) {
				if(suppressedException != null) {
					suppressedException.addSuppressed(e);	
				}
				throw e;
			}			
		}
	}
}
