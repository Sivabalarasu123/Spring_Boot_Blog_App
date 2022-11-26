package com.springboot.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springboot.blog.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	// specific exception
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
			ResourceNotFoundException exception, WebRequest request)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	//global exception
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(
			Exception exception, WebRequest request)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
