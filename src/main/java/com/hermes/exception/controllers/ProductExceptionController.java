package com.hermes.exception.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.hermes.exception.exceptions.AtributosInvalidosException;
import com.hermes.exception.exceptions.InvalidProductFormatException;
import com.hermes.exception.exceptions.ProductNotFoundException;
import com.hermes.exception.exceptions.ProductWithoutNameException;


@RestControllerAdvice
public class ProductExceptionController {
	
	private Map<String, Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        return errorDetails;
    }
	
	@ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
	
	
	@ExceptionHandler(AtributosInvalidosException.class)
	public ResponseEntity<Object> handleAtributoInvalido(AtributosInvalidosException ex){
		return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidProductFormatException.class)
	public ResponseEntity<Object> handleFormatoInvalido(InvalidProductFormatException ex){
		return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProductWithoutNameException.class)
	public ResponseEntity<Object> handleSemNome(ProductWithoutNameException ex){
		return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	/*
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
	        return new ResponseEntity<>(buildErrorResponse("Erro interno do servidor",
	        		HttpStatus.INTERNAL_SERVER_ERROR),
	        		HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	 */
}
