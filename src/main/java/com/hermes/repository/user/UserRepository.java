package com.hermes.repository.user;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.userdetails.UserDetails;

import com.hermes.models.User;

@Configuration
@EnableMongoRepositories(mongoTemplateRef = "DonoTemplate")
public interface UserRepository extends MongoRepository<User, ObjectId> {
	public Optional<UserDetails> findUserByEmail(String Email);
	
	public Optional<User> findByEmail(String Email);
	
	public boolean existsByEmail(String email);
	
	public Optional<User> deleteByEmail(String email);
}
