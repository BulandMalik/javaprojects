package com.thisara.controller.dto.trip;

public class GetTripResponse {

	private int tripId;
	private String tripNumber;
	private String distance;
	private String date;
	private DriverDTO driver;
	private CarDTO car;

	private String recordTimestamp;
	private String recordAge;

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRecordTimestamp() {
		return recordTimestamp;
	}

	public void setRecordTimestamp(String recordTimestamp) {
		this.recordTimestamp = recordTimestamp;
	}

	public String getRecordAge() {
		return recordAge;
	}

	public void setRecordAge(String recordAge) {
		this.recordAge = recordAge;
	}

	public DriverDTO getDriver() {
		return driver;
	}

	public void setDriver(DriverDTO driver) {
		this.driver = driver;
	}

	public CarDTO getCar() {
		return car;
	}

	public void setCar(CarDTO car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "GetTripByCarResponse [tripId=" + tripId + ", tripNumber=" + tripNumber + ", distance=" + distance
				+ ", date=" + date + ", driver=" + driver + ", car=" + car + "]";
	}

}
