// for login, register, profile

package com.example.Liam_Colton_Bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

@GetMapping("/register")
public String showRegisterForm(Model model) {

    

    return "register";
}

@GetMapping("/login")
public String showLoginForm() {

    

    return "login";
}

@GetMapping("/profile")
public String showProfile(Model model) {

    

    return "profile";
}

}