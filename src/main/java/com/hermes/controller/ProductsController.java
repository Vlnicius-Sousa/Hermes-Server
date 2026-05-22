package com.hermes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.dto.atributo.response.ObjectAtributeResponse;
import com.hermes.dto.atributo.response.ProductAtributoResponse;
import com.hermes.dto.product.request.CreateProductRequest;
import com.hermes.dto.product.response.DataResponse;
import com.hermes.models.Atributo;
import com.hermes.models.Product;
import com.hermes.service.ConsultorProductService;
import com.hermes.service.DonoProductService;

import jakarta.validation.Valid;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/produtos")
public class ProductsController {
	ConsultorProductService productService;
	DonoProductService donoService;
	
	public ProductsController(ConsultorProductService prodserv, DonoProductService donoserv) {
		this.productService = prodserv;
		this.donoService = donoserv;
	}
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<List<DataResponse>> GetAllProducts(){
		List<DataResponse> response = productService.getAllProductsResponse();
		
		return ResponseEntity.ok(response);
	}
	
	/*@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<DataResponse> getProduct( @PathVariable(name = "id") ObjectId id){
		Product prod = productService.getProductById(id);
		return ResponseEntity.ok(new DataResponse(prod.getId().toHexString() ,prod.getName(), prod.getAtributos().stream().map(t -> t.toString()).toList()));
	}*/
	
	
	@PostMapping("")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<DataResponse> createProduct(@Valid @RequestBody CreateProductRequest request){
		Product product = donoService.createProduct(request);
		List<ProductAtributoResponse> atributos = product.getAtributos().stream().map(atributo -> {
			ProductAtributoResponse response = new ProductAtributoResponse(atributo.getNome(), atributo.getTipo(), product);
			return response;
		}).toList();
		
		DataResponse response = new DataResponse(product.getId().toHexString(), product.getName(), atributos);
		
		return ResponseEntity.ok(response);
	}
	
	/*@PostMapping("/lote")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<CreatedProductsResponse> createProducts( @RequestBody List<CreateProductRequest> request){
		List<Product> products = donoService.createProducts(request);
		ArrayList<DataResponse> response = new ArrayList<>();
		products.forEach(prod -> response.add(new DataResponse(prod.getId().toHexString(), prod.getName(), prod.getAtributos()))

		);

		return ResponseEntity.ok(new CreatedProductsResponse(response));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteProduct( @PathVariable("id") ObjectId id){
		donoService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> patchProduct(@RequestBody PatchProductRequest modify){
		Product prod = donoService.patchProduct(modify);
		if(prod != null) {
			return ResponseEntity.ok(new DataResponse(prod.getId().toHexString(), prod.getName(), prod.getAtributos()));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PatchMapping("/lote")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<PatchedProdutsResponse> patchProducts(@RequestBody List<PatchProductRequest> modify){
		ArrayList<Product> prod = donoService.patchProducts(modify);
		return ResponseEntity.ok(new PatchedProdutsResponse(prod));		
	}*/
	
}
