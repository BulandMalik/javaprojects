package com.thisara.dao;

import java.util.List;

import com.thisara.domain.Car;
import com.thisara.exception.DAOException;

public interface CarDAO {

	public List<Car> search(String registrationNumber) throws DAOException;
	
	public Car getCar(String registrationNumber) throws DAOException;
	
	public Car getReference(Long id) throws DAOException;
	
	public void save(Car car) throws DAOException;
	
	public void update(Car car) throws DAOException;
	
	public void remove(Car car) throws DAOException;
	
}
