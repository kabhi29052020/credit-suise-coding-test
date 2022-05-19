package com.creditsuise.coding.test.utilities;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.core.io.ClassPathResource;

import com.creditsuise.coding.test.model.Context;

public class FileUtility {

	private Context context;

	public FileUtility(Context context) {
		this.context = context;
	}

	public LineIterator newLineIterator() throws IOException {
		LineIterator lineIterator = FileUtils.lineIterator(new ClassPathResource("data/" + context.getLogFilePath()).getFile(), "UTF-8");
		return lineIterator;
	}
}
