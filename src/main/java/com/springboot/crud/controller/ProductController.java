package com.springboot.crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.crud.model.Product;
import com.springboot.crud.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
		try {
			List<Product> products = new ArrayList<Product>();

			if (name == null)
				productRepository.findAll().forEach(products::add);
			else
				productRepository.findByNameContaining(name).forEach(products::add);

			if (products.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent())
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		try {
			Product productData = productRepository
					.save(new Product(product.getId(), product.getName(), product.getQuantity(), product.getPrice()));
			return new ResponseEntity<>(productData, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
		Optional<Product> existingProduct = productRepository.findById(id);

		if (existingProduct.isPresent()) {
			Product newProduct = existingProduct.get();
			newProduct.setName(product.getName());
			newProduct.setQuantity(product.getQuantity());
			newProduct.setPrice(product.getPrice());
			return new ResponseEntity<>(productRepository.save(newProduct), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteAllProducts")
	public ResponseEntity<Product> deleteAllProducts() {
		try {
			productRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
