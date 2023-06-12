package com.thisara.service;

import java.util.List;

import com.thisara.controller.dto.trip.PostTripDTO;
import com.thisara.domain.Trip;
import com.thisara.exception.ServiceException;

public interface TripService {

	public List<Trip> search(String date, String carRegistrationNumber);
	
	public List<Trip> get(String carRegistrationNumber);
	
	public void save(PostTripDTO postTripDTO);
}
