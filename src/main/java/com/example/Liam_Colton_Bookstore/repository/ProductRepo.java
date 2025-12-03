package com.example.Liam_Colton_Bookstore.repository;

import com.example.Liam_Colton_Bookstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
