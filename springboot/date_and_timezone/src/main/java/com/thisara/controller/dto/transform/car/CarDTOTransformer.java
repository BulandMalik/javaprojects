package com.thisara.controller.dto.transform.car;

import java.util.List;

import com.thisara.controller.dto.car.GetCarResponse;
import com.thisara.domain.Car;

public interface CarDTOTransformer {

	public List<GetCarResponse> getCarResponses(List<Car> carList);
	
	public GetCarResponse getCarResponse(Car car);
}
