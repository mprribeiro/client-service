package com.mprribeiro.clientservice.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mprribeiro.clientservice.services.exceptions.DatabaseIntegrityException;
import com.mprribeiro.clientservice.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setMessage(e.getMessage());
		err.setError("Resource not found.");
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DatabaseIntegrityException.class)
	private ResponseEntity<StandardError> resourceNotFound(DatabaseIntegrityException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setMessage(e.getMessage());
		err.setError("Database integrity.");
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
