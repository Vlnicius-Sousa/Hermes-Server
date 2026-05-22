package com.hermes.repository.dono;

import org.bson.types.ObjectId;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.hermes.models.Product;

@Configuration
@EnableMongoRepositories(mongoTemplateRef = "DonoTemplate" )
public interface DonoProductRepository extends MongoRepository<Product, ObjectId>  {

}
