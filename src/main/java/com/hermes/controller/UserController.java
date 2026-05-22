package com.hermes.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.dto.user.request.DeleteUserRequest;
import com.hermes.dto.user.request.LoginRequest;
import com.hermes.dto.user.request.SignUpRequest;
import com.hermes.dto.user.request.UpdateUserRequest;
import com.hermes.dto.user.response.DeleteUserResponse;
import com.hermes.dto.user.response.LoginResponse;
import com.hermes.dto.user.response.SignUpResponse;
import com.hermes.dto.user.response.UserResponse;
import com.hermes.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	private final UserService userService;
	
	
	public UserController(UserService userService) {
		
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		
		HashMap<String, String> UserNameAndToken = userService.VerifyLoginAndGenerateToken(request);
		
		return ResponseEntity.ok(new LoginResponse(UserNameAndToken.get("userName"), UserNameAndToken.get("token")));
	}
	
	@PostMapping("/signup")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> SignUp(@Valid @RequestBody SignUpRequest request){
		HashMap<String, String> response = userService.createUser(request);
		if (response.size() > 1)
			return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse("Novo usuário criado " + response.get("authorities") , response.get("name"), response.get("email")));
		else
			return ResponseEntity.badRequest().body(new UserResponse("Dados de Signup inválidos, inserir novo email: " + response.get("email")));
		
		
	}
	
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
		
		String resultado = userService.deleteUser(request);
		if("Usuário inexistente".equals(resultado)) {
		return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(new DeleteUserResponse("Usuário deletado, Email utilizado: ", resultado));
		}
	}
	
	@PostMapping("/update/password")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UserResponse> updateUserPassword(@Valid @RequestBody UpdateUserRequest request){
		String message = userService.updatePassword(request);
		return ResponseEntity.ok(new UserResponse(message));
	}
}
