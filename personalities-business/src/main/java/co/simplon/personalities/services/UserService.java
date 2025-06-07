package co.simplon.personalities.services;

import co.simplon.personalities.dtos.UserAuthenticate;
import co.simplon.personalities.dtos.UserCreate;
import co.simplon.personalities.entities.Role;
import co.simplon.personalities.entities.User;
import co.simplon.personalities.repositories.RoleRepository;
import co.simplon.personalities.repositories.UserRepository;
import co.simplon.personalities.config.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
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
    public void create(UserCreate inputs) {
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
