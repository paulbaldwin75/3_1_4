package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private RoleRepository roleRepository;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model, Principal principal){
        User userPrincipal = userService.findByName(principal.getName());
        model.addAttribute("user", userPrincipal);
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.getAllRoles());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("newUser") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "edit";
    }

    @PatchMapping ("/{id}")
    public String update(@ModelAttribute("userEdit") User user, @PathVariable("id") Long id) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
