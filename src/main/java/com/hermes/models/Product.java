package com.hermes.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import org.bson.types.ObjectId;

@Document(collection = "Products")
public class Product {
	
	@Id
	private ObjectId id;
	
	private String name;
	
	@Nullable
	private ArrayList<Atributo> atributos;
	
	public ObjectId getId() {
		return id;
	}
	
	
	public void setId(ObjectId id) {
		this.id = id;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setAtributos(ArrayList<Atributo> atributosAdicionais) {
		this.atributos = atributosAdicionais;
	}
	
	public ArrayList<Atributo> getAtributos() {
		return atributos;
	}
	
	public Product() {
		this.atributos = new ArrayList<Atributo>();
	}
	
	public Product(ObjectId id, String name, ArrayList<Atributo> atributos) {
		this.id=id;
		this.name=name;
		this.atributos = atributos;
	}
	
	public Product(ObjectId id, String name) {
		this.id=id;
		this.name=name;
		this.atributos = null;
	}
	
	@Override
	public String toString() {
		String sep = System.lineSeparator();
		return "Product: {" + sep
		+ "		id: " + this.getId() + ","+ sep
		+ "		name: " + this.getName() +","+ sep
		+ "		atributos: " + String.valueOf(atributos) + sep
		+ "}";
	}
		
}
