package com.webgis.admin;

import com.webgis.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializer {

    @Value("${superadmin.secret}")
    private String adminPassword;

    @Bean
    CommandLineRunner initAdmin(UserService userService) {
        return args -> {
            if (userService.findByUsername("admin").isEmpty()) {
                userService.register(
                        "admin",
                        "Admin",
                        "User",
                        "admin@webgis.com",
                        adminPassword,
                        "SUPERADMIN"
                );
                System.out.println("Admin user created!");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }

}