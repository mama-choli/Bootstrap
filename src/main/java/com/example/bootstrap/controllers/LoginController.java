package com.example.bootstrap.controllers;

import com.example.bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @PostConstruct
    public void init() {
        userService.addInitUsers();
    }
}
