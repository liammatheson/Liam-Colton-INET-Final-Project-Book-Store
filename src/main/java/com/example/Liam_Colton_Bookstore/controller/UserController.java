package com.example.Liam_Colton_Bookstore.controller;

import com.example.Liam_Colton_Bookstore.model.User;
import com.example.Liam_Colton_Bookstore.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session) {

        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }

        session.setAttribute("loggedInUser", user);
        return "redirect:/products";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // TODO: fetch logged-in user info
        return "profile";
    }

    @GetMapping("/") // for redirecting index
    public String redirectToLogin() {
        return "redirect:/login";
    }
}
