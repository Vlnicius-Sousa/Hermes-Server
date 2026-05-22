package com.hermes.dto.user.request;

import jakarta.validation.constraints.NotEmpty;

public record SignUpRequest(@NotEmpty(message = "Email Necessário") String email,
						@NotEmpty(message = "nome necessario") String name,
						@NotEmpty(message = "Senha necessária") String password,
						@NotEmpty(message = "role necessária") String[] roles) {

}
