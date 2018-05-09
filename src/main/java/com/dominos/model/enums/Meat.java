package com.dominos.model.enums;

public enum Meat implements Addable {
	CHORIZO(1.5f, 4), TUNA(1.5f, 8), BACON(1.5f, 2), CHICKEN(1.5f, 3), BEEF(1.5f, 5), PEPPERONI(1.5f, 6), HAM(1.5f, 7)
	;
	
	
	private final float price;
	private final int id;
	
	Meat(float price, int id) {
		this.price = price;
		this.id = id;
	}

	@Override
	public float getPrice() {
		return this.price;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}


}
