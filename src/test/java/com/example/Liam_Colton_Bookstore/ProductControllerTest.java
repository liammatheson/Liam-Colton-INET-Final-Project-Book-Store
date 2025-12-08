package com.example.Liam_Colton_Bookstore;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.Liam_Colton_Bookstore.controller.ProductController;
import com.example.Liam_Colton_Bookstore.model.Product;
import com.example.Liam_Colton_Bookstore.service.ProductService;

@WebMvcTest(ProductController.class) // .\mvnw.cmd test
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testProductsPage() throws Exception {
        mockMvc.perform(get("/products").with(user("testuser")))
               .andExpect(status().isOk())
               .andExpect(view().name("products"));
    }

    @Test
    public void testProductDetailPage() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Book");

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/product/1").with(user("testuser")))
               .andExpect(status().isOk())
               .andExpect(view().name("product-detail"))
               .andExpect(model().attributeExists("product"));
    }
}
