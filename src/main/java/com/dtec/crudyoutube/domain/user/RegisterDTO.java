package com.dtec.crudyoutube.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
