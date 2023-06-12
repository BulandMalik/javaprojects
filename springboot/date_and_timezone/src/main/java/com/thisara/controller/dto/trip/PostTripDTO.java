package com.thisara.controller.dto.trip;

public class PostTripDTO {

	private long carId;
	private long driverId;
	private double distance;

	private String tripDate;
	private String tripTimestamp;
	private String tripTimezone;
	private String tripRecordDate;
	private String tripRecordTimestamp;
	private String tripRecordTimezone;
	private String tripActualDate;
	private String tripActualTimestamp;
	private String tripActualTimezone;

	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getTripDate() {
		return tripDate;
	}

	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}

	public String getTripTimestamp() {
		return tripTimestamp;
	}

	public void setTripTimestamp(String tripTimestamp) {
		this.tripTimestamp = tripTimestamp;
	}

	public String getTripTimezone() {
		return tripTimezone;
	}

	public void setTripTimezone(String tripTimezone) {
		this.tripTimezone = tripTimezone;
	}

	public String getTripRecordDate() {
		return tripRecordDate;
	}

	public void setTripRecordDate(String tripRecordDate) {
		this.tripRecordDate = tripRecordDate;
	}

	public String getTripRecordTimestamp() {
		return tripRecordTimestamp;
	}

	public void setTripRecordTimestamp(String tripRecordTimestamp) {
		this.tripRecordTimestamp = tripRecordTimestamp;
	}

	public String getTripRecordTimezone() {
		return tripRecordTimezone;
	}

	public void setTripRecordTimezone(String tripRecordTimezone) {
		this.tripRecordTimezone = tripRecordTimezone;
	}

	public String getTripActualDate() {
		return tripActualDate;
	}

	public void setTripActualDate(String tripActualDate) {
		this.tripActualDate = tripActualDate;
	}

	public String getTripActualTimestamp() {
		return tripActualTimestamp;
	}

	public void setTripActualTimestamp(String tripActualTimestamp) {
		this.tripActualTimestamp = tripActualTimestamp;
	}

	public String getTripActualTimezone() {
		return tripActualTimezone;
	}

	public void setTripActualTimezone(String tripActualTimezone) {
		this.tripActualTimezone = tripActualTimezone;
	}

	@Override
	public String toString() {
		return "PostTripDTO [carId=" + carId + ", driverId=" + driverId + ", distance=" + distance + ", tripDate="
				+ tripDate + ", tripTimestamp=" + tripTimestamp + ", tripTimezone=" + tripTimezone + ", tripRecordDate="
				+ tripRecordDate + ", tripRecordTimestamp=" + tripRecordTimestamp + ", tripRecordTimezone="
				+ tripRecordTimezone + ", tripActualDate=" + tripActualDate + ", tripActualTimestamp="
				+ tripActualTimestamp + ", tripActualTimezone=" + tripActualTimezone + "]";
	}

}
