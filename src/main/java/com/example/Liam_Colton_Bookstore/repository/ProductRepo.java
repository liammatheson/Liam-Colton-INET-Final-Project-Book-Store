package com.example.Liam_Colton_Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Liam_Colton_Bookstore.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
