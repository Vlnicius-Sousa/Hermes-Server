package com.hermes.dto.user.response;

import java.util.HashMap;

import com.hermes.dto.user.request.LoginRequest;

public record IncorrectCredencials(HashMap<String, Object> errorLog, LoginRequest login) {

}
