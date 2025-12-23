package com.tournament.library_management.controller;
import com.tournament.library_management.dto.LoginRequestDTO;
import com.tournament.library_management.entity.User;
import com.tournament.library_management.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest,
                                   HttpServletRequest request) { // 1. Добавляем параметр
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);


            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);


            Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
            if (user.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Успешный вход в систему");
                response.put("userId", user.get().getId());
                response.put("email", user.get().getEmail());
                response.put("role", "USER");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Пользователь не найден");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неверный email или пароль");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Успешный выход из системы");
    }


    @PostMapping("/create-user")
    public ResponseEntity<String> createUser() {
        String email = "admin@football.com";
        String rawPassword = "password123";

        userRepository.findByEmail(email).ifPresent(user -> userRepository.delete(user));

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName("Администратор");
        newUser.setLastName("Системы");
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        userRepository.save(newUser);

        return ResponseEntity.ok("Пользователь " + email + " пересоздан. Используйте пароль: password123");
    }
}