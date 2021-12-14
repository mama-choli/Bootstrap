package com.example.bootstrap.controllers;

import com.example.bootstrap.model.User;
import com.example.bootstrap.service.RoleService;
import com.example.bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String printUsers(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "/admin";
    }

    @PostMapping("/new")
    public String addNewUser(@ModelAttribute("newUser") User user, @RequestParam("roles") ArrayList<Long> roles) {
        user.setRoles(roles.stream().map(roleService::getRole).collect(Collectors.toSet()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roles) {
        user.setRoles(roles.stream().map(roleService::getRole).collect(Collectors.toSet()));
        userService.updateUser(user);

        return "redirect:/admin";
    }


    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return "redirect:/admin";
    }
}
