package com.thisara.controller.dto.car;

import java.util.List;

public class GetCarResponse {

	private String registrationNumber;
	private String chasisNumber;
	private String chasisColor;
	private String status;
	private String engineNumber;
	private String engineCapacity;

	List<TyreDTO> tyres;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
				+ chasisColor + ", status=" + status + ", engineNumber=" + engineNumber + ", engineCapacity="
				+ engineCapacity + ", tyres=" + tyres + "]";
	}
}
