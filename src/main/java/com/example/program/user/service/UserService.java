package com.example.program.user.service;

import com.example.program.domain.entity.Role;
import com.example.program.domain.entity.User;
import com.example.program.domain.repository.UserRepository;
import com.example.program.exception.NotFoundException;
import java.util.HashSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void grantRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        var roles = new HashSet<>(user.getRoles());
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
