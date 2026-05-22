package com.hermes.dto.product.request;

import java.util.Map;

import com.mongodb.lang.Nullable;

public record DataRequest(String nome, @Nullable Map<String, Object> atributos) {

}
