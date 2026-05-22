package com.hermes.dto.product.response;

import java.util.List;

import com.hermes.models.Product;

public record PatchedProdutsResponse(List<Product> products) {

}
