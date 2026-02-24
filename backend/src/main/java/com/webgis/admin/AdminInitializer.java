package com.webgis.admin;

import com.webgis.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(UserService userService) {
        return args -> {
            if (userService.findByUsername("admin").isEmpty()) {
                userService.register(
                        "admin",
                        "Admin",
                        "User",
                        "admin@webgis.com",
                        "admin123",
                        "SUPERADMIN"
                );
                System.out.println("Admin user created!");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }

}