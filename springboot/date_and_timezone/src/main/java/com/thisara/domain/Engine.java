package com.thisara.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

@Entity
@Table(name = "fl_engine", uniqueConstraints = @UniqueConstraint(name = "number", columnNames = "fe_number"))
public class Engine {

	@Id
	@GeneratedValue
	@Column(name = "fe_id")
	private Long id;

	@NaturalId
	@Column(name = "fe_number")
	private String number;

	@Column(name = "fe_capacity")
	private String capacity;

	@Column(name = "fe_status")
	private Integer status;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "fc_id")
	@JsonIgnore
	private Car car;

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

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
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

}
