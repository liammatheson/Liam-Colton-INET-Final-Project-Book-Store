// for basket and checkout
package com.example.Liam_Colton_Bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

@GetMapping("/basket")
public String showBasket(Model model) {

    

    return "basket";
}

@GetMapping("/checkout")
public String showCheckout(Model model) {

    

    return "checkout";
}

}