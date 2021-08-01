package com.example.httpconverter.domain;

public class Product {
	private String id;
	private String name;
	private String description;

	public Product() {
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description
				+ '\'' + '}';
	}
}
