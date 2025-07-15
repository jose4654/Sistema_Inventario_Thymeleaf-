package com.JOSE.Inventario.DTO;

import java.util.List;

public class LoginResponseDto {
    private String token;
    private String username;
    private List<String> roles;
    private String message;
    private Long id;

    // Constructor vacío
    public LoginResponseDto() {
    }

    // Constructor con todos los campos
    public LoginResponseDto(String token, String username, List<String> roles, String message, Long id) {
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.message = message;
        this.id = id;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
} 