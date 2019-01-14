package com.rafaeldebiase.taskmanager.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rafaeldebiase.taskmanager.service.exception.DataIngretyException;
import com.rafaeldebiase.taskmanager.service.exception.ObjectNotFoundException;

/**
 * @author Rafael de Biase
 *
 */

@ControllerAdvice
public class ResouceExceptionHandler {
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotfound(
			ObjectNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIngretyException.class)
	public ResponseEntity<StandardError> dataIntegrety(
			DataIngretyException e, HttpServletRequest request) {
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}