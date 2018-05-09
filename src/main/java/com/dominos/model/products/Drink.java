package com.dominos.model.products;

import com.dominos.model.exceptions.URLException;

public class Drink extends Product {
	private int drink_id;

	
	
	
	
	public Drink(long id,float price, String pictureUrl, String name) throws URLException {
		super(id,price, pictureUrl, name);
	}
	
	public long getId() {
		return this.id;
		
	}
	
	
}
