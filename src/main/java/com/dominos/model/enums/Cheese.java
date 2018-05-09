package com.dominos.model.enums;

public enum Cheese implements Addable {
	CHEDDAR(1.5f, 1), MOZZARELLA(0, 2), MELTED(1.5f, 3), COW(1.5f, 4), EMMENTAL(1.5f, 5)
	;
	
	
	private final float price;
	private final int id;
	
	Cheese(float price, int id) {
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
		return this.id;
	}


	
	
}
