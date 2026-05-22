package com.hermes.dto.atributo.request;

import jakarta.validation.constraints.NotNull;

public record AtributosRequest(@NotNull String nome, @NotNull String tipo) {

}
