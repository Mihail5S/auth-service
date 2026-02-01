package com.example.program.user.dto;

import jakarta.validation.constraints.NotBlank;

public class GrantRoleRequest {

    @NotBlank
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
