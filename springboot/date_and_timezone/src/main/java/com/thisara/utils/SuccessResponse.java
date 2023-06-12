package com.thisara.utils;

public class SuccessResponse {

	private String message;
	private String status;
	private String timestamp;

	public SuccessResponse(String message, String status, String timestamp) {
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "SuccessResponse [message=" + message + ", status=" + status + ", timestamp=" + timestamp + "]";
	}

}
