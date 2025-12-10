package com.example.Liam_Colton_Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Liam_Colton_Bookstore.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
