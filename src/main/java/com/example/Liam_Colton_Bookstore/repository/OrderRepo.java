package com.example.Liam_Colton_Bookstore.repository;

import com.example.Liam_Colton_Bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
