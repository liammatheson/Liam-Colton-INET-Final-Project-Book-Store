package com.example.Liam_Colton_Bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Liam_Colton_Bookstore.model.User;
import com.example.Liam_Colton_Bookstore.repository.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "register";
        }

        // hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {
        Object error = session.getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error);
            session.removeAttribute("error"); // clear bad login alert
        }
        return "login";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        // look up your User entity from the database
        User user = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("user", user);
        return "profile";
    }


    @GetMapping("/") // for redirecting index
    public String redirectToLogin() {
        return "redirect:/login";
    }
}
