package com.hermes.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Atributos")
public class Atributos {

	@Id
	private ObjectId id;
	
	private String nome;
	
	private String tipo;

	
	
	public String getNome() {
		return nome;
	}



	public String getTipo() {
		return tipo;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public ObjectId getId() {
		return id;
	}

	public Atributos() {}

	public Atributos(ObjectId id, String nome, String tipo) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}
	
}
