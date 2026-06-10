package com.hermes.dto.product.request;

import java.util.Map;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PatchProductRequest(@NotNull(message="id necessario para a modificação") ObjectId id
								, @NotEmpty(message = "necessario algo a ser modificado") Map<String, Object> patch) {

}
