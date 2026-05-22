package com.hermes.dto.product.request;

import java.util.List;
import java.util.Map;

import com.hermes.models.Atributo;
import com.mongodb.lang.Nullable;

import jakarta.validation.constraints.NotEmpty;

public record CreateProductRequest(@NotEmpty(message= "name é obrigátorio") String nome
									,@NotEmpty(message = "ao menos um atributo é necessário") List<Atributo> atributos) {

}