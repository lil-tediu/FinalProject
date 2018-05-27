package com.dominos.model.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dominos.model.address.Address;
import com.dominos.model.products.Product;
import com.dominos.model.user.User;

public class Order {
	private long id;
	private LocalDateTime datetime;
	private User user;
	private HashMap<Product, Integer> products;
	private float price;
	private Address address;
	private boolean isDelivered;
	
	
	
	public Order() {
		this.products = new HashMap<Product, Integer>();
		this.isDelivered = false;
		this.price = 0;
		this.datetime = LocalDateTime.now();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HashMap<Product, Integer> getProducts() {
		return new HashMap<Product, Integer>(this.products);
	}

	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Address getAddres() {
		return this.address;
	}

	public void setAddres(Address address) {
		this.address = address;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}
	
	public static double calculatePriceForCart(HashMap<Product, Integer> products) {
		double cartPrice = 0;
		for (Entry<Product, Integer> entry : products.entrySet()) {
			Product product = entry.getKey();
			int quantity = entry.getValue();
			double productPrice = product.getPrice();
			cartPrice += (productPrice * quantity);
		}
		return cartPrice;
	}
	
	public void addProduct(Product product, int quantity) {
		this.products.put(product, quantity);
	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
	}
}
