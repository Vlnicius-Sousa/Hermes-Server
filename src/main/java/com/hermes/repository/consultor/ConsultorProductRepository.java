package com.hermes.repository.consultor;

import java.util.Optional;

import org.bson.types.ObjectId;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.hermes.models.Product;

@Configuration
@EnableMongoRepositories(mongoTemplateRef = "ConsultorTemplate" )
public interface ConsultorProductRepository extends MongoRepository<Product, ObjectId> {
}
