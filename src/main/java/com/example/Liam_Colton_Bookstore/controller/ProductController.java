// for products
package com.example.Liam_Colton_Bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

@GetMapping("/products")
public String showProducts(Model model) {

    

    return "products";
}



}