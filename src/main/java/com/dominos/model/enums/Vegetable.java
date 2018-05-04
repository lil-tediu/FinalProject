package com.dominos.model.enums;

public enum Vegetable implements Addable {
	MUSHROOM(1.5f, 1), ONION(1.5f, 2), PICKLES(1.5f, 3), OLIVES(1.5f, 4), TOMATOES(1.5f, 5), PINEAPPLE(1.5f, 6),
	CORN(1.5f, 7), PESTO(1.5f, 8);
	;
	
	
	private final float price;
	private final int id;
	
	Vegetable(float price, int id) {
		this.price = price;
		this.id = id;
	}

	@Override
	public float getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
