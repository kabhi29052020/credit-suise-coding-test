package com.creditsuise.coding.test.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.creditsuise.coding.test.model.Context;

@Component
public class ArgumentValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentValidator.class);

	public void validateInput(Context context, String... args) throws IOException {
		LOGGER.info("Validating the input...");
		validateArguments(args);
		validateFilePath(context, args[0]);
	}

	private void validateFilePath(Context context, String logFilePath) throws IOException {
		LOGGER.info("Log file for analysis: {}", logFilePath);
		context.setLogFilePath(logFilePath);

		try {
			File file = new ClassPathResource("data/" + logFilePath).getFile();
			if (!file.exists()) {
				file = new ClassPathResource(logFilePath).getFile();
				if (!file.exists()) {
					file = new File(logFilePath);
				}
			}
			if (!file.exists())
				throw new FileNotFoundException("Unable to open the file " + logFilePath);
		} catch (IOException e) {
			LOGGER.error("Unable to find the specified file '{}'", logFilePath);
			throw e;
		}
	}

	private void validateArguments(String[] args) {
		LOGGER.debug("Validating the program arguments...");
		if (args.length < 1 || args.length > 1) {
			throw new IllegalArgumentException("!!! Please specify the file to analyze.");
		}
	}
}
