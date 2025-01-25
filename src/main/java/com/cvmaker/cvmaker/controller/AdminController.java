package com.cvmaker.cvmaker.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cvmaker.cvmaker.dto.UserDto;
import com.cvmaker.cvmaker.entity.User;
import com.cvmaker.cvmaker.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "admin/create-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/create")
    public String createUser(UserDto userDto) {
        userService.registerUser(userDto);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, UserDto userDto) {
        userService.updateUser(id, userDto);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}