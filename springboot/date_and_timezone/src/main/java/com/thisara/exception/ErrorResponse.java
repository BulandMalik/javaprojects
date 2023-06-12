package com.thisara.exception;

public class ErrorResponse {

	private String error;
	private String status;
	private String timestamp;

	public ErrorResponse(String error, String status, String timestamp) {
		this.error = error;
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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
		return "ErrorResponse [error=" + error + ", status=" + status + ", timestamp=" + timestamp + "]";
	}
}
