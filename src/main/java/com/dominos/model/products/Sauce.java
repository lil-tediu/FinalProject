package com.dominos.model.products;

import com.dominos.model.exceptions.URLException;

public class Sauce extends Product {
	private int sauce_id;
	
	
	public Sauce(long id,float price, String pictureUrl, String name) throws URLException {
		super(id, price, pictureUrl, name);
	}
	
	public long getId() {
		return this.id;
	}
}
