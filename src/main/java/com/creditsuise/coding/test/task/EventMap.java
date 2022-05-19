package com.creditsuise.coding.test.task;

import java.util.Map;

import com.creditsuise.coding.test.model.Event;

public class EventMap {
	/**
	 * EventMap temporarily holds the events while we find the matching - STARTED or FINISHED events.
	 * Once found, the corresponding event would be removed from this map.
	 */
	private Map<String, Event> eventMap;

	public EventMap(Map<String, Event> eventMap) {
		this.setEventMap(eventMap);
	}

	/**
	 * @return the eventMap
	 */
	public Map<String, Event> getEventMap() {
		return eventMap;
	}

	/**
	 * @param eventMap the eventMap to set
	 */
	public void setEventMap(Map<String, Event> eventMap) {
		this.eventMap = eventMap;
	}
}
