package com.dominos.model.enums;

public enum Spice implements Addable {
	BASIL(0f, 2), OREGANO(0f, 1), PARMESAN_SNOWFLAKES(0.5f, 3)
	;
	
	
	private final float price;
	private final int id;
	
	Spice(float price, int id) {
		this.price = price;
		this.id = id;
	}

	@Override
	public float getPrice() {
		
		return this.price;
	}

	@Override
	public int getId() {
		return this.id;
	}


}
