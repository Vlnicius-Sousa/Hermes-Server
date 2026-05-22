package com.hermes.dto.product.request;

import java.util.Map;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotEmpty;

public record PatchProductRequest(@NotEmpty(message="id necessario para a modificação") ObjectId id
								, @NotEmpty(message = "necessario algo a ser modificado") Map<String, Object> patch) {

}
