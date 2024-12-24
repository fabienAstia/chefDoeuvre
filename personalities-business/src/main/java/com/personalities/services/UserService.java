package com.personalities.services;

import com.personalities.config.JwtProvider;
import com.personalities.dto.UserAuthenticate;
import com.personalities.dto.UserCreate;
import com.personalities.entities.Role;
import com.personalities.entities.User;
import com.personalities.repositories.UserRepository;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void create(UserCreate inputs) {
        User user = new User();
        user.setUsername(inputs.username());
        user.setPassword(passwordEncoder.encode(inputs.password()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public Object authenticate(UserAuthenticate inputs) {
        String username = inputs.username();
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException(username));

        if (!passwordEncoder.matches(inputs.password(), user.getPassword())) {
            throw new BadCredentialsException(username);
        }
        return jwtProvider.create(username);
    }
}
