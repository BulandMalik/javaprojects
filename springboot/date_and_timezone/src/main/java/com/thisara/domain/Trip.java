package com.thisara.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "fl_trip")
public class Trip {

	@Id
	@GeneratedValue
	@Column(name = "ft_id")
	private Long id;

	@Column(name = "ft_number")
	private String number;

	@Column(name = "ft_distance")
	private Double distance;

	@Column(name = "ft_trip_date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "ft_trip_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name = "ft_trip_timezone")
	private String timezone;

	@Column(name = "ft_record_date")
	private java.sql.Date recordDate;

	@Column(name = "ft_record_timestamp")
	private java.sql.Timestamp recordTimestamp;

	@Column(name = "ft_record_timezone")
	private String recordTimezone;

	@Column(name = "ft_actual_trip_date")
	private LocalDate actualTripdate;

	@Column(name = "ft_actual_trip_timestamp")
	private ZonedDateTime actualTripTimestamp;

	@Column(name = "ft_actual_timezone")
	private String actualTimezone;

	@Column(name = "ft_status")
	private Integer status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fc_id")
	@JsonIgnore
	private Car car;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fd_id")
	private Driver driver;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public java.sql.Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(java.sql.Date recordDate) {
		this.recordDate = recordDate;
	}

	public java.sql.Timestamp getRecordTimestamp() {
		return recordTimestamp;
	}

	public void setRecordTimestamp(java.sql.Timestamp recordTimestamp) {
		this.recordTimestamp = recordTimestamp;
	}

	public LocalDate getActualTripdate() {
		return actualTripdate;
	}

	public void setActualTripdate(LocalDate actualTripdate) {
		this.actualTripdate = actualTripdate;
	}

	public ZonedDateTime getActualTripTimestamp() {
		return actualTripTimestamp;
	}

	public void setActualTripTimestamp(ZonedDateTime actualTripTimestamp) {
		this.actualTripTimestamp = actualTripTimestamp;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getRecordTimezone() {
		return recordTimezone;
	}

	public void setRecordTimezone(String recordTimezone) {
		this.recordTimezone = recordTimezone;
	}

	public String getActualTimezone() {
		return actualTimezone;
	}

	public void setActualTimezone(String actualTimezone) {
		this.actualTimezone = actualTimezone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

}
