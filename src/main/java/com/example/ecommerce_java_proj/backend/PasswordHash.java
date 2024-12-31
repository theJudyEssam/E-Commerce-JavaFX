package com.example.ecommerce_java_proj.backend;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//https://dzone.com/articles/secure-password-hashing-in-java


public class PasswordHash{

    public static String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, storedHash);
    }


    }