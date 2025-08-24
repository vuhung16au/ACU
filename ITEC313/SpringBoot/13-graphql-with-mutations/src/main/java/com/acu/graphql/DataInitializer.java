package com.acu.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userRepository.findByUsername("313@acu.com").isPresent()) {
            User adminUser = new User();
            adminUser.setUsername("313@acu.com");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println("Default admin user created: 313@acu.com");
        }
    }
}
