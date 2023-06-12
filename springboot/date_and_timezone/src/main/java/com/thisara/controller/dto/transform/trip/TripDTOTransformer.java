package com.thisara.controller.dto.transform.trip;

import java.util.List;

import com.thisara.controller.dto.trip.GetTripResponse;
import com.thisara.domain.Trip;

public interface TripDTOTransformer {

	public List<GetTripResponse> carPageTripList(List<Trip> tripList, String timezone);
}
