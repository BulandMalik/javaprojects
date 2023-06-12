package com.thisara.controller.dto.car;

public class TyreDTO {

	private String number;
	private String year;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "TyreDTO [number=" + number + ", year=" + year + "]";
	}
}
