package com.hermes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoClientsConfig {
	
	@Value("${mongodb.pass.consultor}")
	private String consultorPass;

	@Value("${mongodb.pass.dono}")
	private String donoPass;

	@Value("${mongodb.pass.usuarios}")
	private String usuariosPass;

	@Value("${mongodb.pass.atributos}")
	private String atributosPass;

	// Conexão com usuário Consultor (somente leitura)
    @Bean(name = "consultorTemplate")
    @Primary
    MongoTemplate consultorTemplate() {
        MongoClient client = MongoClients.create("mongodb://Consultor:"+consultorPass+"@localhost:27017/HermesDB");
        return new MongoTemplate(client, "HermesDB");
    }
    @Bean(name = "DonoTemplate")
    MongoTemplate DonoTemplate() {
        MongoClient client = MongoClients.create("mongodb://Dono:"+donoPass+"@localhost:27017/HermesDB");
        return new MongoTemplate(client, "HermesDB");
    }
    @Bean(name = "ConsultorUsuariosTemplate")
    MongoTemplate ConsultorUsuariosTemplate() {
        MongoClient client = MongoClients.create("mongodb://ConsultorCriador:"+usuariosPass+"@localhost:27017/Users");
        return new MongoTemplate(client, "Users");
    }
    
    @Bean(name = "ConsultorAtributosTemplate")
    MongoTemplate ConsultorAtributosTemplate() {
        MongoClient client = MongoClients.create("mongodb://ConsultorCriadorAtributos:"+atributosPass+"@localhost:27017/Preferences");
        return new MongoTemplate(client, "Preferences");
    }
}
