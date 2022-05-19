package com.creditsuise.coding.test.task;

import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditsuise.coding.test.config.LogConfiguration;
import com.creditsuise.coding.test.model.Context;
import com.creditsuise.coding.test.model.Event;
import com.creditsuise.coding.test.model.State;
import com.creditsuise.coding.test.model.persistence.Alert;
import com.creditsuise.coding.test.queue.AlertBlockingQueue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventTask.class);
	
	private EventMap eventMap;
	private LogConfiguration logConfiguration;
	private String line;
	private Context context;

	public EventTask(EventMap eventMap, LogConfiguration applicationData, String line, Context context) {
		this.eventMap = eventMap;
		this.logConfiguration = applicationData;
		this.line = line;
		this.context = context;
	}

	@Override
	public void run() {
		Event event = null;
		try {			
			event = new ObjectMapper().readValue(line, Event.class); 
			LOGGER.trace("{}", event);

			// Check if we have either STARTED or FINISHED event already for the given ID.
			// If yes, then find the execution time between STARTED and FINISHED states and update the alert.
			if (eventMap.getEventMap().containsKey(event.getId())) {
				Event e1 = eventMap.getEventMap().get(event.getId());
				long executionTime = getEventExecutionDuration(event, e1);

				// the alert created off an event would have the alert flag set to FALSE by default.
				Alert alert = new Alert(event, Math.toIntExact(executionTime), context);

				// if the execution time is more than the specified threshold, flag the alert as TRUE
				if (executionTime > logConfiguration.getAlertThresholdMs()) {
					alert.setAlert(Boolean.TRUE);
					LOGGER.trace("*** Execution time for the event {} is {}ms ***", event.getId(), executionTime);
				}
				
				// add generated alert to alert queue that are yet to be persisted
				LOGGER.info("**** Enqueing alert to queue for further processing. Alert: {} ****", alert);
				AlertBlockingQueue.getInstance().put(alert);
				
				// remove from the temporary map as we found the matching event
				eventMap.getEventMap().remove(event.getId());
			} else {
				eventMap.getEventMap().put(event.getId(), event);
			}
		} catch (JsonProcessingException e) {
			/**
			 * Print full Stack trace
			 */
			LOGGER.error("Unable to parse the event! {}", event, e);
		} catch (InterruptedException e) {
			/**
			 * Print full Stack trace
			 */
			LOGGER.error("{} interrupted while putting alert to queue", Thread.currentThread().getName(), event, e);
		}
	}

	private long getEventExecutionDuration(Event event1, Event event2) {
		Event endEvent = Stream.of(event1, event2).filter(e -> State.FINISHED.equals(e.getState())).findFirst().orElse(null);
		Event startEvent = Stream.of(event1, event2).filter(e -> State.STARTED.equals(e.getState())).findFirst().orElse(null);
		return Objects.requireNonNull(endEvent).getTimestamp() - Objects.requireNonNull(startEvent).getTimestamp();
	}
}
