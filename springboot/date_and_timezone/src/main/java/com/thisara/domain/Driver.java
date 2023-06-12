package com.thisara.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "fl_driver")
public class Driver {

	@Id
	@GeneratedValue
	@Column(name = "fd_id")
	private Long id;

	@Column(name = "fd_license_number")
	private String licenceNumber;

	@Column(name = "fd_first_name")
	private String firstName;

	@Column(name = "fd_last_name")
	private String lastName;

	@Column(name = "fd_status")
	private Integer status;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "fl_trips", joinColumns = @JoinColumn(name = "fd_id"), inverseJoinColumns = @JoinColumn(name = "fc_id"))
	private Set<Car> cars = new LinkedHashSet<Car>();

	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Trip> trips = new ArrayList<Trip>();

	public void addTrip(Trip trip) {
		trips.add(trip);
		trip.setDriver(this);
	}

	public void removeTrip(Trip trip) {
		trips.remove(trip);
		trip.setDriver(null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Driver))
			return false;

		return id != null && id.equals(((Driver) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
