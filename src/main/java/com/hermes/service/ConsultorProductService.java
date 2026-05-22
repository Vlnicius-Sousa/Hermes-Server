package com.hermes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.hermes.dto.atributo.response.ObjectAtributeResponse;
import com.hermes.dto.atributo.response.ProductAtributoResponse;
import com.hermes.dto.product.response.DataResponse;
import com.hermes.exception.exceptions.ProductNotFoundException;
import com.hermes.models.Atributo;
import com.hermes.models.Product;
import com.hermes.repository.consultor.ConsultorProductRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;



@Service
public class ConsultorProductService {
	private final ConsultorProductRepository consultorProductRepository;

	public ConsultorProductService(ConsultorProductRepository repo) {
		this.consultorProductRepository = repo;
	}
	
	public List<Product> getAllProducts(){

		List<Product> products = consultorProductRepository.findAll();
		
			if(products == null || products.isEmpty()) {
				throw new ProductNotFoundException("Não existem produtos");
			}	
		
		return products;
	}
	
	public Product getProductById(ObjectId id) {
		return consultorProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Produto não encontrado pelo id: " + id));
	}
	
	public List<DataResponse> getAllProductsResponse(){
		List<Product> produtos = getAllProducts();
		
		List<DataResponse> response = produtos.stream().map(produto -> {
			String id = produto.getId().toHexString();
			String nome = produto.getName();
			ArrayList<Atributo> atributos = produto.getAtributos();
			ArrayList<ProductAtributoResponse> atributores = new ArrayList<ProductAtributoResponse>(
					atributos.stream().map(atributo -> {
						Object valor = atributo.getValor();
						
						if(valor instanceof String || valor instanceof Number || valor instanceof Boolean) {
							return new ProductAtributoResponse(atributo.getNome(), atributo.getTipo(), atributo.getValor());
						} else {
							ObjectMapper mapper = new ObjectMapper();
							String objstr = mapper.writeValueAsString(atributo.getValor());
							
							JsonNode obj = mapper.readTree(objstr);
							
							HashMap<String, Object> objAtrib = new HashMap<String, Object>();

							obj.forEachEntry((key, value) -> {
								if(value.isDouble()) {
									objAtrib.put(key, value.asDouble());
								} else if (value.isString()) {
									objAtrib.put(key, value.stringValue());
								} else if(value.isInt()) {
									objAtrib.put(key, value.asInt());
								}
							});
							
							return new ProductAtributoResponse(atributo.getNome(), atributo.getTipo(), objAtrib);
						}
					}).toList());
			
			return new DataResponse(id, nome, atributores);
		}).toList();
		
		
		return response;
		
	}
	
}
