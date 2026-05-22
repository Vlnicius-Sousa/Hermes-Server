package com.hermes.repository.atributos;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.hermes.models.Atributos;

@Configuration
@EnableMongoRepositories(mongoTemplateRef = "ConsultorAtributosTemplate" )
public interface AtributosRepository extends MongoRepository<Atributos, ObjectId> {

}
