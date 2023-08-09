package com.example.buland.springboot.security.jwt.v1.repositories;

import com.example.buland.springboot.security.jwt.v1.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
