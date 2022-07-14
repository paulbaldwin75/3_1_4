package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping
    public String getUsers(Model model, Principal principal){
        User userPrincipal = userService.findByName(principal.getName());
        model.addAttribute("userPrincipal", userPrincipal);
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("roles", userService.getAllRoles());
        return "allUsers";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.getAllRoles());
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("newUser") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "/edit";
    }

    @PatchMapping
    public String update(@ModelAttribute("userEdit") User user) {

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping
    public String delete(@ModelAttribute("userDelete") User user) {
        Long id = user.getId();
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
