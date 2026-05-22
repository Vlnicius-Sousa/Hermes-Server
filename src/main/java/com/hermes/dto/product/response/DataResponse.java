package com.hermes.dto.product.response;

import java.util.List;
import java.util.Map;

import com.hermes.dto.atributo.response.AtributosResponse;
import com.hermes.dto.atributo.response.ProductAtributoResponse;
import com.hermes.models.Atributo;
import com.mongodb.lang.Nullable;

public record DataResponse(String id, String name, @Nullable List<ProductAtributoResponse> atributos) {

}
