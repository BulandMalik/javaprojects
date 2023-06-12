package com.thisara.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thisara.controller.dto.transform.trip.TripDTOTransformer;
import com.thisara.controller.dto.trip.GetTripResponse;
import com.thisara.controller.dto.trip.PostTripDTO;
import com.thisara.domain.Trip;
import com.thisara.service.TripService;
import com.thisara.utils.SuccessResponse;

@RestController
@RequestMapping("/trips")
public class TripController {

	private static Logger logger = Logger.getLogger(TripController.class.getName());

	@Autowired
	public TripService tripService;

	@Autowired
	public TripDTOTransformer tripDTOTransformer;

	@GetMapping("/search/{carId}")
	public ResponseEntity<List<GetTripResponse>> search(@PathVariable("carId") String carRegistrationNumber,
			@RequestParam("fromDate") String fromDate, @RequestParam("timezone") String timezone) {

		List<Trip> tripList = tripService.search(fromDate, carRegistrationNumber);

		List<GetTripResponse> tripListDtos = tripDTOTransformer.carPageTripList(tripList, timezone);

		return new ResponseEntity<List<GetTripResponse>>(tripListDtos, HttpStatus.OK);
	}

	@GetMapping("/{carId}")
	public ResponseEntity<List<GetTripResponse>> get(@PathVariable("carId") String carId) {

		List<Trip> tripList = tripService.get(carId);

		List<GetTripResponse> tripListDtos = tripDTOTransformer.carPageTripList(tripList, "Europe/Paris");

		return new ResponseEntity<List<GetTripResponse>>(tripListDtos, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<SuccessResponse> persist(PostTripDTO postTripDTO) {

		tripService.save(postTripDTO);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Created", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}
}
