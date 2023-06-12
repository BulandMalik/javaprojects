package com.thisara.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thisara.controller.dto.car.GetCarResponse;
import com.thisara.controller.dto.transform.car.CarDTOTransformer;
import com.thisara.domain.Car;
import com.thisara.domain.Engine;
import com.thisara.exception.ServiceException;
import com.thisara.service.CarService;
import com.thisara.utils.SuccessResponse;

@RestController
@RequestMapping("/cars")
public class CarController {

	private static Logger logger = Logger.getLogger(CarController.class.getName());

	@Autowired
	private CarService carService;

	@Autowired
	private CarDTOTransformer carDTOTransformer;

	@GetMapping("/search/{regNo}")
	public ResponseEntity<List<GetCarResponse>> search(@PathVariable("regNo") String registrationNumber)
			throws ServiceException {

		List<Car> carList = carService.search(registrationNumber);

		List<GetCarResponse> carDtos = carDTOTransformer.getCarResponses(carList);

		return new ResponseEntity<List<GetCarResponse>>(carDtos, HttpStatus.OK);
	}

	@GetMapping("/{regNo}")
	public ResponseEntity<GetCarResponse> get(@PathVariable("regNo") String registrationNumber) {

		Car car = carService.getCar(registrationNumber);

		GetCarResponse carDTO = carDTOTransformer.getCarResponse(car);

		return new ResponseEntity<GetCarResponse>(carDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<SuccessResponse> persist(Car car, Engine engine, String tyreData) throws ServiceException {

		carService.save(car, engine, tyreData);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Created", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponse> remove(String carId) {

		carService.remove(carId);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Removed", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}
}
