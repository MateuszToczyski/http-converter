package com.example.httpconverter.controller;

import com.example.httpconverter.domain.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

	@PostMapping
	public void create(@RequestBody Product product) {
		System.out.println(product);
	}
}
