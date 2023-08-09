package com.example.buland.springboot.security.jwt.v1.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.example.buland.springboot.security.jwt.v1.entities.Product;
import com.example.buland.springboot.security.jwt.v1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    /**
     * Create a new Product
     *
     * @param product
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        Product savedProduct = repo.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

    /**
     * Get All products
     * @return List<Product>
     */
    @GetMapping
    public List<Product> list() {
        return repo.findAll();
    }
}
