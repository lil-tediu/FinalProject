package com.dominos.model.enums;

public interface Addable {
	float getPrice();
	int getId();
	static Addable toEnum(String string) {
			for (Cheese x : Cheese.values()) {
				if (string.equals(x.toString())) {
					return x;
				}
			}
			for (Meat x : Meat.values()) {
				if (string.equals(x.toString())) {
					return x;
				}
			}
			for (Spice x : Spice.values()) {
				if (string.equals(x.toString())) {
					return x;
				}
			}
			for (Vegetable x : Vegetable.values()) {
				if (string.equals(x.toString())) {
					return x;
				}
			}
			return null;
	}
	
}
