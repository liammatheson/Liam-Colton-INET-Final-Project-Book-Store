package com.example.Liam_Colton_Bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.Liam_Colton_Bookstore.model.Basket;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class BasketHeader {

   @ModelAttribute("cartItems")
    public List<Basket> populateBasket(HttpSession session) {
        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        return basket != null ? basket : new ArrayList<>();
    }

    @ModelAttribute("cartCount")
public int basketCount(HttpSession session) {
    List<Basket> basket = (List<Basket>) session.getAttribute("basket");

    if (basket == null) return 0;

    int total = 0;
    for (Basket item : basket) {
        total += item.getQuantity();
    }

    return total;
}

@ModelAttribute("cartTotal")
public double basketTotal(HttpSession session) {
    List<Basket> basket = (List<Basket>) session.getAttribute("basket");
    if (basket == null) return 0.0;

    return basket.stream()
                 .mapToDouble(Basket::getTotalPrice)
                 .sum();
}

    
}

