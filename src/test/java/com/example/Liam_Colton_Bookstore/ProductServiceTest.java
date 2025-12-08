package com.example.Liam_Colton_Bookstore;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.Liam_Colton_Bookstore.model.Product;
import com.example.Liam_Colton_Bookstore.repository.ProductRepo;
import com.example.Liam_Colton_Bookstore.service.ProductService;

public class ProductServiceTest {

    @Mock
    private ProductRepo productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Book");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);
        assertNotNull(result);
    }

    @Test
    public void testGetAllProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Book One");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Book Two");

        List<Product> products = Arrays.asList(p1, p2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();
        assertNotNull(result);
    }
}
