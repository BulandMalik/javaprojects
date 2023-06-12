package com.thisara.controller.dto.trip;

public class DriverDTO {

	private int driverId;
	private String licenceNumber;
	private String firstName;
	private String lastName;

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "TripDriverDTO [driverId=" + driverId + ", licenceNumber=" + licenceNumber + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
