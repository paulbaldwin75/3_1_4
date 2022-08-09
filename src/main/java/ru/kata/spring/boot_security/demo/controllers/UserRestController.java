package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserRepository userRepo;

    public UserRestController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return ResponseEntity.ok(userRepo.findByUsername(principal.getName()));
    }
}
