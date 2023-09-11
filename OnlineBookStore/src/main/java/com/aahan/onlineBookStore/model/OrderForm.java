package com.aahan.onlineBookStore.model;

import java.time.LocalDateTime;

public class OrderForm {
	private LocalDateTime dateTime;
	private String status;

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
