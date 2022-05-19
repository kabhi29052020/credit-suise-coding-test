package com.creditsuise.coding.test.model.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.creditsuise.coding.test.model.Context;
import com.creditsuise.coding.test.model.Event;
import com.creditsuise.coding.test.model.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "alerts")
public class Alert {
	@Id
	@JsonProperty("id")
	private String id;

	@JsonProperty("duration")
	private int duration;

	@JsonProperty("type")
	private EventType type;

	@JsonProperty("host")
	private String host;

	@JsonProperty("alert")
	private Boolean alert;

	@Transient
	private Context context;

	public Alert() {
	}

	public Alert(Event event, int duration, Context context) {
		this.id = event.getId();
		this.type = event.getType();
		this.host = event.getHost();
		this.duration = duration;
		this.alert = Boolean.FALSE;
		this.context = context;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
