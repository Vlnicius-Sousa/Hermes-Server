package com.hermes.exception.exceptions;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String Message) {
		super(Message);
	}
}
