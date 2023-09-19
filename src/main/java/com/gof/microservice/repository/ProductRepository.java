package com.gof.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gof.microservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
