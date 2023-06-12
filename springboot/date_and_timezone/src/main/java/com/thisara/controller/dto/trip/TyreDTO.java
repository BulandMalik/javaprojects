package com.thisara.controller.dto.trip;

public class TyreDTO {

	private String id;
	private String tyreNumber;
	private String year;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTyreNumber() {
		return tyreNumber;
	}

	public void setTyreNumber(String tyreNumber) {
		this.tyreNumber = tyreNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "TyreDTO [id=" + id + ", tyreNumber=" + tyreNumber + ", year=" + year + "]";
	}
}
