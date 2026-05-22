package com.hermes.dto.user.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(	@NotEmpty(message = "Email necessario") String email,
						@NotEmpty(message = "Senha necessária") String password) {}
