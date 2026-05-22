package com.hermes.dto.user.request;

public record UpdateUserRequest(String email, String password, String newPassword) {

}
