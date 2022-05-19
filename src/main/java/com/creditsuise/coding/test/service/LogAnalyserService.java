package com.creditsuise.coding.test.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditsuise.coding.test.manager.LogAnalyserManager;
import com.creditsuise.coding.test.model.Context;
import com.creditsuise.coding.test.validator.ArgumentValidator;

@Service
public class LogAnalyserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyserService.class);

	@Autowired
	private ArgumentValidator validator;

	@Autowired
	private LogAnalyserManager manager;

	public void execute(String... args) throws IOException {
		Context context = Context.newInstance();
		validator.validateInput(context, args);
		try {
			manager.processEvents(context);
		} catch (IOException e) {
			/**
			 * non-recoverable error, better to throw it and stop processing
			 */
			LOGGER.error("Exception occurred while processing event", e);
			throw e;
		}
	}
}
