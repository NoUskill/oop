package com.tournament.library_management.config;

import com.tournament.library_management.entity.*;
import com.tournament.library_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
//@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Проверка и создание тестовых пользователей...");
        createTestUsers();
    }

    private void createTestUsers() {
        if (userRepository.count() == 0) {
            String commonHashedPassword = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi";

            User admin = new User(
                    "admin@football.com",
                    commonHashedPassword,
                    "Администратор",
                    "Системы"
            );
            User user = new User(
                    "user@football.com",
                    commonHashedPassword,
                    "Иван",
                    "Иванов"
            );

            userRepository.save(admin);
            userRepository.save(user);
            System.out.println("Тестовые пользователи созданы.");
        }
    }
}