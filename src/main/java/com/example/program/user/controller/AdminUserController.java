package com.example.program.user.controller;

import com.example.program.domain.entity.Role;
import com.example.program.exception.BadRequestException;
import com.example.program.user.dto.GrantRoleRequest;
import com.example.program.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/roles")
    public String grantRole(@PathVariable Long userId, @Valid @RequestBody GrantRoleRequest req) {
        Role role;
        try {
            role = Role.valueOf(req.getRole().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Unknown role: " + req.getRole());
        }

        userService.grantRole(userId, role);
        return "ok";
    }
}
