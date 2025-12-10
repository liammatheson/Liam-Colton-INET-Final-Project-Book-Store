package com.example.Liam_Colton_Bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Liam_Colton_Bookstore.model.Order;
import com.example.Liam_Colton_Bookstore.model.User;


public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    public List<Order> findByUserId(Long id);

}
