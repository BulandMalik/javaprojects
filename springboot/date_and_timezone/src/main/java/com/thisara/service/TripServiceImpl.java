package com.thisara.service;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisara.controller.dto.trip.PostTripDTO;
import com.thisara.dao.CarDAO;
import com.thisara.dao.DriverDAO;
import com.thisara.dao.TripDAO;
import com.thisara.domain.Car;
import com.thisara.domain.Driver;
import com.thisara.domain.Trip;
import com.thisara.exception.DAOException;
import com.thisara.utils.DateTimeParser;
import com.thisara.utils.SQLDateParser;
import com.thisara.utils.UtilDateParser;

@Service
public class TripServiceImpl implements TripService{
	
	private static final Logger logger = Logger.getLogger(TripServiceImpl.class.getName());

	@Autowired
	private TripDAO tripDAO;
	
	@Autowired
	private CarDAO carDAO;
	
	@Autowired
	private DriverDAO driverDAO;
	
	@Override
	public List<Trip> search(String date, String carRegistrationNumber) {
		
		List<Trip> trips = null;
				
		try {
			
		trips =	tripDAO.search(date, carRegistrationNumber);
		
		} catch (DAOException e) {
			logger.severe(e.getMessage());
		}
		
		return trips;
	}

	@Override
	public List<Trip> get(String carRegistrationNumber) {
		
		List<Trip> trips = null;
		
		try {
		
		trips = tripDAO.get(carRegistrationNumber);
		
		} catch (DAOException e) {
			logger.severe(e.getMessage());
		}
		
		return trips;
	}

	@Override
	@Transactional
	public void save(PostTripDTO postTripDTO) {
		
		try {
		
		Trip trip = new Trip();
		
		Car car = carDAO.getReference(postTripDTO.getCarId());
		Driver driver = driverDAO.getReference(postTripDTO.getDriverId());
		
		trip.setCar(car);
		trip.setDriver(driver);
		trip.setDistance(postTripDTO.getDistance());
		
		trip.setDate(UtilDateParser.parseDate(postTripDTO.getTripDate()));
		trip.setTimestamp(UtilDateParser.parseTimestamp(postTripDTO.getTripTimestamp(), postTripDTO.getTripTimezone()));
		trip.setTimezone(postTripDTO.getTripTimezone());
		
		trip.setRecordDate(SQLDateParser.parseDate(postTripDTO.getTripDate()));
		trip.setRecordTimestamp(SQLDateParser.parseTimestamp(postTripDTO.getTripRecordTimestamp()));
		trip.setRecordTimezone(postTripDTO.getTripRecordTimezone());
		
		trip.setActualTripdate(DateTimeParser.parseDate(postTripDTO.getTripDate()));
		trip.setActualTripTimestamp(DateTimeParser.parseTimestamp(postTripDTO.getTripActualTimestamp(), postTripDTO.getTripActualTimezone()));
		trip.setActualTimezone(postTripDTO.getTripActualTimezone());
		
		tripDAO.save(trip);
		
		}catch (DAOException e) {
			logger.severe(e.getMessage());
		}
	}

}
