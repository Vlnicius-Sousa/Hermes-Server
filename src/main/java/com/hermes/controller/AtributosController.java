package com.hermes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.dto.atributo.request.AtributosRequest;
import com.hermes.dto.atributo.response.AtributosResponse;
import com.hermes.models.Atributos;
import com.hermes.service.AtributosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atributos")
public class AtributosController {

	private AtributosService atributosService;
	
	public AtributosController(AtributosService atributos) {
		this.atributosService = atributos;
	}

	@GetMapping("")
	public ResponseEntity<List<AtributosResponse>> obtemAtributos(){
		
		List<Atributos> atributos = atributosService.obterTodosAtributos();
		
		List<AtributosResponse> response = new ArrayList<>();
		
		atributos.forEach(atributo -> {
				response.add(new AtributosResponse(atributo.getNome(), atributo.getTipo()));
		});
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<AtributosResponse> criarAtributo(@Valid @RequestBody AtributosRequest request) {
		Atributos atrib = atributosService.criarAtributo(request);
		
		AtributosResponse response = new AtributosResponse(atrib.getNome(), atrib.getTipo());
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/lote")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<AtributosResponse>> criarAtributosLote(@Valid @RequestBody List<AtributosRequest> request){
		List<Atributos> atributos = atributosService.criarAtributos(request);

		List<AtributosResponse> response = new ArrayList<>();
		
		atributos.forEach(atributo -> {
			response.add(new AtributosResponse(atributo.getNome(), atributo.getTipo()));
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
