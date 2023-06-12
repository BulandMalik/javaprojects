package com.thisara.dao;

import java.util.List;

import com.thisara.domain.Trip;
import com.thisara.exception.DAOException;

public interface TripDAO {

	public List<Trip> search(String date, String carRegistrationNumber) throws DAOException;
	
	public List<Trip> get(String carRegistrationNumber) throws DAOException;
	
	public void save(Trip trip) throws DAOException;
}
