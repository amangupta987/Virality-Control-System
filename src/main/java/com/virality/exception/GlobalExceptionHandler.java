package com.virality.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TooManyRequestsException.class)
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public String hanldeTooManyRequests(TooManyRequestsException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(CooldownException.class)
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public String handleCooldown(CooldownException ex) {
		return ex.getMessage();
	}
	
	
}
