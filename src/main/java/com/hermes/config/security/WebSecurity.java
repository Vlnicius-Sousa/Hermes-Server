package com.hermes.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	public WebSecurity(SecurityFilter securityFilter) {
		super();
		this.securityFilter = securityFilter;
	}
	private final SecurityFilter securityFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http.csrf(csrf -> csrf.disable())
		 	 .cors(cors -> cors.configure(http))
		 	 .sessionManagement(session -> 
		 	 	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 	 .authorizeHttpRequests((authorize) ->
		 	 authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
		 	 .anyRequest().authenticated())
		 	 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		 
		 return http.build();
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permite qualquer origem nos testes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	@Bean
	AuthenticationManager authenticationManeger(AuthenticationConfiguration authConfig) throws Exception  {
		return authConfig.getAuthenticationManager();
	}
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BcryptPassword4jPasswordEncoder();
	}
}
