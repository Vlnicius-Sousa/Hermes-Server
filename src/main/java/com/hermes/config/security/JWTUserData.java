package com.hermes.config.security;

public record JWTUserData(Long userId, String email, String[] authorities) {
	public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private String email;
        private String[] authorities;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder authorities(String [] authorities) {
        	this.authorities = authorities;
        	return this;
        }

        public JWTUserData build() {
            return new JWTUserData(userId, email, authorities);
        }
    }
}
