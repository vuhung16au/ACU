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
        // Create admin users if they don't exist
        if (!userRepository.findByUsername("313@acu.com").isPresent()) {
            User adminUser = new User();
            adminUser.setUsername("313@acu.com");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println("Admin user created: 313@acu.com");
        }
        
        if (!userRepository.findByUsername("admin@acu.com").isPresent()) {
            User adminUser2 = new User();
            adminUser2.setUsername("admin@acu.com");
            adminUser2.setPassword(passwordEncoder.encode("123456"));
            adminUser2.setRole("ADMIN");
            userRepository.save(adminUser2);
            System.out.println("Admin user created: admin@acu.com");
        }
        
        // Create normal users if they don't exist
        if (!userRepository.findByUsername("user@acu.com").isPresent()) {
            User normalUser = new User();
            normalUser.setUsername("user@acu.com");
            normalUser.setPassword(passwordEncoder.encode("123456"));
            normalUser.setRole("USER");
            userRepository.save(normalUser);
            System.out.println("Normal user created: user@acu.com");
        }
        
        if (!userRepository.findByUsername("000@acu.com").isPresent()) {
            User normalUser2 = new User();
            normalUser2.setUsername("000@acu.com");
            normalUser2.setPassword(passwordEncoder.encode("123456"));
            normalUser2.setRole("USER");
            userRepository.save(normalUser2);
            System.out.println("Normal user created: 000@acu.com");
        }
    }
}
