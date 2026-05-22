package com.hermes.exception.exceptions;

import org.jspecify.annotations.Nullable;

import com.hermes.dto.user.request.LoginRequest;

public class CredenciaisIncorretasException extends RuntimeException {
	LoginRequest req;
	
	public CredenciaisIncorretasException(@Nullable String msg) {
		super(msg);
	}
	
	public CredenciaisIncorretasException(@Nullable String msg, LoginRequest request) {
		super(msg);
		this.req = request;
	}
	
	public LoginRequest getRequest () {
		return this.req;
	}

}
