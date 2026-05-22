package com.hermes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.hermes.dto.product.request.CreateProductRequest;
import com.hermes.dto.product.request.PatchProductRequest;
import com.hermes.exception.exceptions.ProductNotFoundException;
import com.hermes.models.Atributo;
import com.hermes.models.Product;
import com.hermes.repository.dono.DonoProductRepository;
import com.hermes.validator.ProductValidator;


@Service
public class DonoProductService {
	private final DonoProductRepository donoProductRepository;
	private final ProductValidator validator;
	
	public DonoProductService(DonoProductRepository donoRepo, ProductValidator validator) {
		this.donoProductRepository=donoRepo;
		this.validator = validator;
	}
	
	public Product createProduct(CreateProductRequest request) {

		Product prod = validator.validate(request.nome(), request.atributos());
		
		prod.setId(new ObjectId());
		
		return donoProductRepository.save(prod);
	}

	/*public List<Product> createProducts(List<CreateProductRequest> request) {
		List<Product> products = request.stream()
										.map(prod -> new Product(new ObjectId()
																, prod.name()
																, Optional.of((HashMap<String, Object>) prod.atributos())
																.orElseGet(HashMap<String, Object>::new)))
										.toList();
		
		return donoProductRepository.saveAll(products);
	}
	
	public Product deleteProduct(ObjectId id) {
		Optional<Product> prod = donoProductRepository.findById(id);
		prod.orElseThrow(() -> new ProductNotFoundException("Produto não existe"));
		donoProductRepository.deleteById(id);
		return prod.get();
	}
	
	public Product patchProduct(PatchProductRequest request) {
		Optional<Product> search = donoProductRepository.findById(request.id());
		if(search.isPresent()) {
			Product prod = search.get();
			HashMap<String, Object> thingsToChange = (HashMap<String, Object>) request.patch();
			thingsToChange.forEach((field, value) -> {
				if(field.equals("name")) {
					prod.setName((String)value);
				} else{
					prod.getAtributos().put(field, value);
				}
			});
			return donoProductRepository.save(prod);
		} else {
			return null;
		}
	}

	public ArrayList<Product> patchProducts(List<PatchProductRequest> modify) {
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		modify.forEach(ProductsToFind -> {
			Optional<Product> p = donoProductRepository.findById(ProductsToFind.id());
			if(p.isPresent()) {
				Product product = p.get();
				HashMap<String, Object> thingToChange = new HashMap<String, Object>(ProductsToFind.patch());
				thingToChange.forEach((field, value) -> {
					if(field.equals("name")) {
						product.setName((String)value);
					} else {
						product.getAtributos().put(field, value);
					}
				});
				products.add(product);
			}			
		});
		
		return new ArrayList<Product>(donoProductRepository.saveAll(products));
	}*/
	
}
