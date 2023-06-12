package com.thisara.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "fl_car")
public class Car {

	@Id
	@GeneratedValue
	@Column(name = "fc_id")
	private Long id;

	@Column(name = "fc_reg_number")
	private String registrationNumber;

	@NaturalId
	@Column(name = "fc_chasis_number")
	private String chasisNumber;

	@Column(name = "fc_chasis_color")
	private String chasisColor;

	@Column(name = "fc_status")
	private Integer status;

	@OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Engine engine;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tyre> tyres = new ArrayList<Tyre>();

	@ManyToMany(mappedBy = "cars")
	private Set<Driver> drivers = new LinkedHashSet<Driver>();

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Trip> trips = new ArrayList<Trip>();

	public void addTrip(Trip trip) {
		trips.add(trip);
		trip.setCar(this);
	}

	public void removeTrip(Trip trip) {
		trips.remove(trip);
		trip.setCar(null);
	}

	public void addSeat(Tyre seat) {
		tyres.add(seat);
		seat.setCar(this);
	}

	public void removeSeat(Tyre seat) {
		tyres.remove(seat);
		seat.setCar(null);
	}

	public void addEngine(Engine engine) {
		if (engine == null) {
			if (this.engine != null) {
				this.engine.setCar(null);
			}
		} else {
			engine.setCar(this);
		}
		this.engine = engine;
	}

	public void addDriver(Driver driver) {
		drivers.add(driver);
		driver.getCars().add(this);
	}

	public void removeDriver(Driver driver) {
		drivers.remove(driver);
		driver.getCars().remove(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public List<Tyre> getTyres() {
		return tyres;
	}

	public void setTyres(List<Tyre> tyres) {
		this.tyres = tyres;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public Set<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(Set<Driver> drivers) {
		this.drivers = drivers;
	}

	public List<Trip> getCars() {
		return trips;
	}

	public void setCars(List<Trip> trips) {
		this.trips = trips;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Car))
			return false;

		Car car = (Car) o;
		return Objects.equals(chasisNumber, car.chasisNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(chasisNumber);
	}

}
