package com.hermes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.hermes.repository.consultor",
    mongoTemplateRef = "consultorTemplate"
)
class ConsultorMongoConfig { }

@Configuration
@EnableMongoRepositories(
    basePackages = "com.hermes.repository.dono",
    mongoTemplateRef = "DonoTemplate"
)
class DonoMongoConfig { }

@Configuration
@EnableMongoRepositories(
    basePackages = "com.hermes.repository.user",
    mongoTemplateRef = "ConsultorUsuariosTemplate"
)
class ConsultorUsuariosMongoConfig {}

@Configuration
@EnableMongoRepositories(
    basePackages = "com.hermes.repository.atributos",
    mongoTemplateRef = "ConsultorAtributosTemplate"
)
class ConsultorAtributosMongoConfig {}