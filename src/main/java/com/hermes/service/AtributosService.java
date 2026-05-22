package com.hermes.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.hermes.dto.atributo.request.AtributosRequest;
import com.hermes.models.Atributos;
import com.hermes.repository.atributos.AtributosRepository;

@Service
@ComponentScan
public class AtributosService {

	private final AtributosRepository atributosRepository;
	
	public AtributosService(AtributosRepository atributosRepository) {
		this.atributosRepository = atributosRepository;
	}

	public List<Atributos> obterTodosAtributos(){
		return atributosRepository.findAll();
	}
	
	public Atributos criarAtributo(AtributosRequest request) {
		Atributos atributo = new Atributos(new ObjectId(), request.nome(), request.tipo());
		
		return atributosRepository.save(atributo);
	}
	
	public List<Atributos> criarAtributos(List<AtributosRequest> request) {
		ArrayList<Atributos> atributos = new ArrayList<Atributos>();
		
		request.forEach(atrib -> {
			atributos.add(new Atributos(new ObjectId(), atrib.nome(), atrib.tipo()));
		});
		
		
		
		return atributosRepository.saveAll(atributos);
	}
}
