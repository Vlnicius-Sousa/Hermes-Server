package com.hermes.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Atributo {
	String nome;
	String tipo;
	Object valor;
	
	
	public Atributo(String nome, String tipo, Object valor) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.valor = valor;
	}

	public Atributo() {
		super();
	}
	
	@JsonGetter(value = "nome")
	public String getNome() {
		return nome;
	}
	
	@JsonGetter(value = "tipo")
	public String getTipo() {
		return tipo;
	}
	
	@JsonGetter(value = "valor")
	public Object getValor() {
		return valor;
	}

	@JsonSetter(value = "nome")
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@JsonSetter(value = "tipo")
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@JsonSetter(value = "valor")
	public void setValor(Object valor) {
		this.valor = valor;
	}
	
	
}
