package com.hermes.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Document(collection = "users")
public class User implements UserDetails {
	
	
	@Id
	private ObjectId id;
	private String name;
	private String password;
	private String email;
	private HashSet<String> roles;
	
	public User(ObjectId id, String name, String password, String email, HashSet<String> roles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	
	public User() {}

	public void setRoles(String role) {
		if(this.roles == null) {
			this.roles = new HashSet<String>();
		}
		this.roles.add(role);
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}
	public ObjectId getid() {
		
		return this.id;
	}

}
