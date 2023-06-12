package com.thisara.exception;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

	private static final Logger logger = Logger.getLogger(ExceptionAdvice.class.getName());

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException e) {
		logger.severe(e.getMessage());
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("Duplicate Object",
				HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.severe(e.getMessage());
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ServiceException.class })
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
		logger.severe(e.getMessage());
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("Duplicate Object",
				HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
	}
}
