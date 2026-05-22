package com.hermes.dto.product.response;

import java.util.List;

import com.hermes.models.Product;

public record CreatedProductsResponse(List<DataResponse> products) {

}
