package com.raystech.JobApplicationSystemApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {
//    @GetMapping("/login")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login"; // → src/main/resources/templates/login.html
    }
}
