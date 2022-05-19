package com.creditsuise.coding.test.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.creditsuise.coding.test.model.Event;
import com.creditsuise.coding.test.model.EventType;
import com.creditsuise.coding.test.model.State;

public class LogFileGenerator {

	private LogFileGenerator() {

	}

	public static void generateFile(String path, int numberOfEvents) {
		List<Event> events = new ArrayList<>();

		for (int i = 0; i < numberOfEvents * 2; i++) {
			String id = generateRandomUuid();
			@SuppressWarnings("unused")
			State state = chooseFrom(State.STARTED, State.FINISHED);
			EventType type = chooseFrom(EventType.APPLICATION_LOG, null);
			long timestamp = System.currentTimeMillis();
			String host = chooseFrom(generateRandomIp(), null);
			Event startEvent = new Event();
			startEvent.setId(id);
			startEvent.setState(State.STARTED);
			startEvent.setHost(host);
			startEvent.setType(type);
			startEvent.setTimestamp(timestamp);

			Event endEvent = new Event();
			endEvent.setId(id);
			endEvent.setState(State.FINISHED);
			endEvent.setHost(host);
			endEvent.setType(type);
			endEvent.setTimestamp((long) (timestamp + Math.random() * System.nanoTime() % 10));

			events.add(startEvent);
			events.add(endEvent);
		}
		writeToFile(path, events);
	}

	private static void writeToFile(String path, List<Event> events) {
		System.out.println(">>> Writing to " + path);
		File file = null;
		FileOutputStream fileOutputStream = null;
		try {
			file = new File(path);
			file.createNewFile();
			fileOutputStream = new FileOutputStream(file);
			for (Event e : events) {
				fileOutputStream.write(e.toString().getBytes(StandardCharsets.UTF_8));
				fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));
			}
			System.out.println("Size: " + Math.round(file.length() / 1024.0) + "KB");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SafeVarargs
	private static <T> T chooseFrom(T... values) {
		return values[(int) (Math.random() * System.nanoTime() % values.length)];
	}

	public static String generateRandomIp() {
		return (int) (Math.random() * System.nanoTime() % 255) + "." + (int) (Math.random() * System.nanoTime() % 255)
				+ "." + (int) (Math.random() * System.nanoTime() % 255) + "."
				+ (int) (Math.random() * System.nanoTime() % 255);
	}

	public static String generateRandomUuid() {
		String seed = "e0A1f2E3D4a5Bd6b7c8CF9";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 16; i++) {
			sb.append(seed.charAt((int) ((Math.random() * System.nanoTime()) % seed.length())));
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		LogFileGenerator.generateFile("src/main/resources/data/huge-sample-log-file.txt", 100);
	}
}
