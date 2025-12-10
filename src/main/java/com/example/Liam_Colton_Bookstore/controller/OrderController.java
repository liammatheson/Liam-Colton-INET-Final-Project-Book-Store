package com.example.Liam_Colton_Bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Liam_Colton_Bookstore.model.Basket;
import com.example.Liam_Colton_Bookstore.model.Order;
import com.example.Liam_Colton_Bookstore.model.Product;
import com.example.Liam_Colton_Bookstore.model.User;
import com.example.Liam_Colton_Bookstore.repository.OrderRepo;
import com.example.Liam_Colton_Bookstore.repository.ProductRepo;
import com.example.Liam_Colton_Bookstore.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/basket")
    public String viewBasket(HttpSession session, Model model) {
        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        if (basket == null) basket = new ArrayList<>();
        model.addAttribute("basket", basket);

        double total = basket.stream().mapToDouble(Basket::getTotalPrice).sum(); //show the total price of the basket as a double
        String formattedTotal = String.format("%.2f", total);
        model.addAttribute("total", total);

        return "basket";
    }

    @PostMapping("/basket/add/{id}")
    public String addToBasket(@PathVariable Long id, HttpSession session) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) return "redirect:/products";

        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        if (basket == null) basket = new ArrayList<>();

        boolean found = false;
        for (Basket item : basket) {
            if (item.getProduct().getId().equals(id)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) basket.add(new Basket(product, 1));

        session.setAttribute("basket", basket);
        return "redirect:/basket";
    }

    @PostMapping("/basket/remove/{id}")
    public String removeFromBasket(@PathVariable Long id, HttpSession session) {
        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        if (basket != null) {
            basket.removeIf(item -> item.getProduct().getId().equals(id));
            session.setAttribute("basket", basket);
        }
        return "redirect:/basket";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<Basket> basket = (List<Basket>) session.getAttribute("basket");
        if (basket == null) basket = new ArrayList<>();
        double total = basket.stream().mapToDouble(Basket::getTotalPrice).sum();
        model.addAttribute("basket", basket);
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/checkout/complete")
    public String completeCheckout(HttpSession session) {

    List<Basket> basket = (List<Basket>) session.getAttribute("basket");
    if (basket == null || basket.isEmpty()) {
        return "redirect:/basket";
    }
    //Get logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    User user = userRepo.findByEmail(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

    //Convert basket
    List<Product> products = new ArrayList<>();
    List<Integer> quantities = new ArrayList<>();
    double total = 0;
    for (Basket item : basket) {
        products.add(item.getProduct());
        quantities.add(item.getQuantity());
        total += item.getTotalPrice();
    }


    //Create order
    Order order = new Order(user, products, quantities, total);
    orderRepo.save(order);

    //Empty basket
    session.removeAttribute("basket");
    return "order-confirmation";
    }

}
