package com.hermes.exception.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.hermes.dto.user.response.IncorrectCredencials;
import com.hermes.exception.exceptions.CredenciaisIncorretasException;

@RestControllerAdvice
public class UserExceptionController {
	
	private HashMap<String, Object> buildErrorResponse(String message, HttpStatus status) {
        HashMap<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("messagem", message);
        return errorDetails;
    }
	@ExceptionHandler(CredenciaisIncorretasException.class)
    public ResponseEntity<Void> handleCredenciaisIncorretas(CredenciaisIncorretasException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
