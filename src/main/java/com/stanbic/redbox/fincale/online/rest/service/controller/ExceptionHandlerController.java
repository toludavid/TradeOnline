package com.stanbic.redbox.fincale.online.rest.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stanbic.redbox.fincale.online.rest.service.dto.FinacleTradingResponse;
import com.stanbic.redbox.fincale.online.rest.service.exception.AuthorizationException;
import com.stanbic.redbox.fincale.online.rest.service.exception.DuplicateResourceException;
import com.stanbic.redbox.fincale.online.rest.service.exception.InvalidInputException;
import com.stanbic.redbox.fincale.online.rest.service.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

	public FinacleTradingResponse finacleTradingResponse;

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
		FinacleTradingResponse finacleTradingResponse = new FinacleTradingResponse();
		finacleTradingResponse.setResponseCode("404");
		finacleTradingResponse.setResponseMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(finacleTradingResponse);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<?> handleDuplicateResourceException(DuplicateResourceException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(InvalidInputException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex) {
		FinacleTradingResponse finacleTradingResponse = new FinacleTradingResponse();
		finacleTradingResponse.setResponseCode("400");
		finacleTradingResponse.setResponseMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(finacleTradingResponse);
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<?> handleMissingRequestHeaderException( MissingRequestHeaderException ex)
			throws JsonProcessingException {
 
		finacleTradingResponse = new FinacleTradingResponse();
		finacleTradingResponse.setResponseCode("401");
		finacleTradingResponse.setResponseMessage("Authentication is required to access this resource");
		log.info("InvalidInputException Response {}" , finacleTradingResponse);
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(finacleTradingResponse);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleAuthorizationException( AuthorizationException ex)
			 {
		finacleTradingResponse = new FinacleTradingResponse();
		finacleTradingResponse.setResponseCode("401");
		finacleTradingResponse.setResponseMessage(ex.getMessage());
		log.info("UNAUTHORIZED Response {}" , finacleTradingResponse);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(finacleTradingResponse);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> handleGenericException(Exception ex) throws JsonProcessingException {
 
		finacleTradingResponse = new FinacleTradingResponse();
		finacleTradingResponse.setResponseCode("500");
		finacleTradingResponse.setResponseMessage("Internal Server Error Occurred");
 		log.info("Response {}" , finacleTradingResponse);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(finacleTradingResponse);
	}
}