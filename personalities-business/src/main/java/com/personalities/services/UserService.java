package com.personalities.services;

import com.personalities.config.JwtProvider;
import com.personalities.dto.UserAuthenticate;
import com.personalities.dto.UserCreate;
import com.personalities.entities.Role;
import com.personalities.entities.User;
import com.personalities.repositories.RoleRepository;
import com.personalities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final String adminUsername;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider, @Value("${admin.username}") String adminUsername) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.adminUsername = adminUsername;
    }

    @Transactional
    public ResponseEntity<Object> create(UserCreate inputs) {
        User user = new User();
        user.setUsername(inputs.username());
        user.setPassword(passwordEncoder.encode(inputs.password()));
        if (user.getUsername().equals(adminUsername)) {
            Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
            user.setRole(roleAdmin);
        } else {
            Role roleUser = roleRepository.findByRole("ROLE_USER");
            user.setRole(roleUser);
        }
        userRepository.save(user);
        return null;
    }

    public Object authenticate(UserAuthenticate inputs) {
        String username = inputs.username();
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException(username));
        if (!passwordEncoder.matches(inputs.password(), user.getPassword())) {
            throw new BadCredentialsException(username);
        }
        String role = user.getRole().getRole();
        return jwtProvider.create(username, role);
    }
}
