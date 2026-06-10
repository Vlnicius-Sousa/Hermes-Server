package com.hermes.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hermes.config.security.TokenConfig;
import com.hermes.dto.user.request.DeleteUserRequest;
import com.hermes.dto.user.request.LoginRequest;
import com.hermes.dto.user.request.SignUpRequest;
import com.hermes.dto.user.request.UpdateUserRequest;
import com.hermes.exception.exceptions.CredenciaisIncorretasException;
import com.hermes.models.User;
import com.hermes.repository.user.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManeger;
	private final TokenConfig tokenConfig;
	
	public UserService(UserRepository userrepo,PasswordEncoder passwEncode, TokenConfig tokenConfig, AuthenticationManager authManeger) {
		this.userRepository = userrepo;
		this.passwordEncoder = passwEncode;
		this.authManeger = authManeger;
		this.tokenConfig = tokenConfig;
	}
	
	public Optional<UserDetails> getUser(String Email){
		return userRepository.findUserByEmail(Email);
	}
	
	public User VerifyUserCredentials (String email, String password) {
		try {
			UsernamePasswordAuthenticationToken UserAndPass = new UsernamePasswordAuthenticationToken(email, password);
			Authentication authentication = authManeger.authenticate(UserAndPass);
			User user = (User) authentication.getPrincipal();
			return user;
		} catch (BadCredentialsException e) {
			throw new CredenciaisIncorretasException("Credenciais incorretas", new LoginRequest(email, password));
		}
	}
	
	public HashMap<String, String> createUser (SignUpRequest request){
		
		HashMap<String, String> response = new HashMap<String, String>();
		String email = request.email();
		
		if (emailExists(email)){
			response.put("email", email);
			return response;
		}
		User novoUser = new User();
		novoUser.setEmail(email);
		novoUser.setName(request.name());
		novoUser.setPassword(passwordEncoder.encode(request.password()));
		
		String[] roles = request.roles();
		for(int i = 0; i < roles.length; i++) {
			String authority = roles[i];
			authority.toUpperCase();
			novoUser.setRoles(authority);
		}
		
		
		userRepository.save(novoUser);
		
		
		response.put("email", novoUser.getEmail());
		response.put("name", novoUser.getUsername());
		response.put("authorities", AuthorityUtils.authorityListToSet(novoUser.getAuthorities()).toString());
		return response;
	}
	
	public String deleteUser (DeleteUserRequest request) {
		Optional<User> user = userRepository.deleteByEmail(request.email());
		if(user.isPresent()) {
			 User deleteduser = user.get();
			 String email = deleteduser.getEmail();
			 
			 return email;
		}
		return "Usuário inexistente";
	}

	public HashMap<String, String> VerifyLoginAndGenerateToken(LoginRequest request) {
		User user = VerifyUserCredentials(request.email(), request.password());
		String token = tokenConfig.genereteToken(user);
		
		HashMap<String, String> userNameAndToken = new HashMap<String, String>();
		userNameAndToken.put("token", token);
		userNameAndToken.put("userName", user.getUsername());
		return userNameAndToken;	
	}
	
	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public String updatePassword(UpdateUserRequest updateRequest) {
			User user = VerifyUserCredentials(updateRequest.email(), updateRequest.password());
			user.setPassword(passwordEncoder.encode(updateRequest.newPassword()));
			userRepository.save(user);
			
			return "troca feita";
	}
		
		
	
	
	
}
