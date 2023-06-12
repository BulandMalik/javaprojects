package com.thisara.controller.dto.transform.car;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thisara.controller.dto.car.GetCarResponse;
import com.thisara.domain.Car;

@Component
public class CarDTOTransformerImpl implements CarDTOTransformer {

	@Autowired
	public ModelMapper modelMapper;

	public Logger logger = Logger.getLogger(CarDTOTransformerImpl.class.getName());

	@Override
	public List<GetCarResponse> getCarResponses(List<Car> carList) {

		List<GetCarResponse> carDTOs = carList.stream().map(source -> modelMapper.map(source, GetCarResponse.class))
				.collect(Collectors.toList());

		return carDTOs;
	}

	@Override
	public GetCarResponse getCarResponse(Car car) {

		GetCarResponse carDTO = modelMapper.map(car, GetCarResponse.class);

		return carDTO;
	}
}
