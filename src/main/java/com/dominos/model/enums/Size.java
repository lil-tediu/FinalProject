package com.dominos.model.enums;

public enum Size {
	MEDIUM(1), BIG(1.21f), JUMBO(1.46f);
	
	private final float coefficient;
	Size(float coefficient) {
		this.coefficient = coefficient;
	}
	
	
	public float getCoefficient() {
		return this.coefficient;
	}
	
}
