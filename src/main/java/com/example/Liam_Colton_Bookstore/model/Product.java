package com.example.Liam_Colton_Bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;
    @Lob // i had to add this or the max characters was 255. it makes the limit bigger.
    private String description;
    private double price;
    private String imageUrl;

    public Product() {}

    /*

INSERT INTO products (name, author, description, price, image_url)
VALUES ('Book A', 'Author', 'Test description', 19.99, 'https://example.com/a.jpg');

    
    */

    public Product(String name, String author, String description, double price, String imageUrl) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAuthor() {
        return author;
    }

}
