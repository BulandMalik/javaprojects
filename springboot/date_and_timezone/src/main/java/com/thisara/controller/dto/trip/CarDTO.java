package com.thisara.controller.dto.trip;

import java.util.List;

public class CarDTO {

	private String registrationNumber;
	private String chasisNumber;
	private String chasisColor;
	private String engineNumber;
	private String engineCapacity;
	private List<TyreDTO> tyres;

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getChasisNumber() {
		return chasisNumber;
	}

	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	public String getChasisColor() {
		return chasisColor;
	}

	public void setChasisColor(String chasisColor) {
		this.chasisColor = chasisColor;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(String engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public List<TyreDTO> getTyres() {
		return tyres;
	}

	public void setTyres(List<TyreDTO> tyres) {
		this.tyres = tyres;
	}

	@Override
	public String toString() {
		return "CarDTO [registrationNumber=" + registrationNumber + ", chasisNumber=" + chasisNumber + ", chasisColor="
				+ chasisColor + ", engineNumber=" + engineNumber + ", engineCapacity=" + engineCapacity + ", tyres="
				+ tyres + "]";
	}
}
