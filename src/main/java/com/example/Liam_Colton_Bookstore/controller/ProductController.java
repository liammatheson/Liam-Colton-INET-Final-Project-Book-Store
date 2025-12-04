// for products
package com.example.Liam_Colton_Bookstore.controller;

import com.example.Liam_Colton_Bookstore.model.Product;
import com.example.Liam_Colton_Bookstore.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product-details";
    }
}
