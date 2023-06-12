package com.thisara.service;

import java.util.List;

import com.thisara.domain.Car;
import com.thisara.domain.Engine;
import com.thisara.exception.ServiceException;

public interface CarService {

	public List<Car> search(String registrationNumber)throws ServiceException;
	
	public Car getCar(String registrationNumber);
	
	public void save(Car car, Engine engine, String tyreData) throws ServiceException;
	
	public void update(Car car);
	
	public void remove(String carId);
}
