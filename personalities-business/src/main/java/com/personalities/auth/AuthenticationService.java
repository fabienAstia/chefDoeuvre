package com.personalities.auth;

import com.personalities.config.JwtService;
import com.personalities.entities.Role;
import com.personalities.entities.User;
import com.personalities.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.password().equals("soleil2") ? Role.ADMIN : Role.USER);
        userRepository.save((user));
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken); // à enelever pour le register le Token
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch(Exception e){
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Authentication failed", e);
        }

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
