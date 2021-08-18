package com.springboot.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.crud.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Product findByName(String name);

	List<Product> findByNameContaining(String name);


}
