package com.thisara.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thisara.dao.CarDAO;
import com.thisara.dao.EngineDAO;
import com.thisara.dao.TyreDAO;
import com.thisara.domain.Car;
import com.thisara.domain.Engine;
import com.thisara.domain.Tyre;
import com.thisara.exception.DAOException;
import com.thisara.exception.ServiceException;

@Service
public class CarServiceImpl implements CarService {

	private Logger logger = Logger.getLogger(CarServiceImpl.class.getName());

	@Autowired
	private CarDAO carDAO;

	@Autowired
	private EngineDAO engineDAO;

	@Autowired
	private TyreDAO tyreDAO;

	@Override
	public Car getCar(String registrationNumber) {
		Car car = null;

		try {

			car = carDAO.getCar(registrationNumber);

		} catch (DAOException e) {
			logger.severe(e.getMessage());
		}

		return car;
	}

	@Override
	public List<Car> search(String registrationNumber) throws ServiceException {

		List<Car> cars = null;

		try {

			cars = carDAO.search(registrationNumber);

		} catch (DAOException e) {
			logger.severe(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

		return cars;
	}

	@Override
	@Transactional
	public void save(Car car, Engine engine, String tyreData) throws ServiceException {
		try {

			engine.setCar(car);

			carDAO.save(car);

			engineDAO.save(engine);

			ObjectMapper mapper = new ObjectMapper();

			List<Tyre> tyres = Arrays.asList(mapper.readValue(tyreData, Tyre[].class));

			for (Tyre tyre : tyres) {

				tyre.setCar(car);

				tyreDAO.save(tyre);
			}

		} catch (JsonProcessingException e) {
			logger.severe(e.getMessage());
			throw new ServiceException("SER-101");
		} catch (DAOException d) {
			logger.severe(d.getMessage());
			throw new ServiceException(d.getMessage());
		} catch (Exception d) {
			logger.severe(d.getMessage());
			throw new ServiceException(d.getMessage());
		}
	}

	@Override
	public void update(Car car) {

		try {

			carDAO.update(car);

		} catch (DAOException e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void remove(String carId) {

		Car car = null;

		try {

			car = carDAO.getReference(Long.parseLong(carId));

			carDAO.remove(car);

		} catch (DAOException e) {
			logger.severe(e.getMessage());
		}
	}
}
