package com.dominos.model.products;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.dominos.model.enums.Addable;
import com.dominos.model.enums.Dough;
import com.dominos.model.enums.PizzaSauce;
import com.dominos.model.enums.Size;
import com.dominos.model.exceptions.URLException;

public class CustomPizza extends Product {

	private static final int MAXIMUM_SUPPLEMENTS = 12;
	private static final String name = "Your pizza";
	private static final float MINIMUM_PRIZE_CUSTOM_PIZZA = 6.50f;

	private long customPizza_id;
	private Dough dough;
	private Size size;
	private PizzaSauce sauce;
	private Set<Addable> supplements;
	private Set<String> stringSupplements;
	private String stringSize;
	private String stringDough;
	private String stringPizzaSauce;

	public CustomPizza() {
		super();
		this.stringDough = "TRADITIONAL";
		this.stringSize = "MEDIUM";
		this.stringPizzaSauce = "TOMATO";
		this.dough = Dough.TRADITIONAL;
		this.size = Size.MEDIUM;
		this.sauce = PizzaSauce.TOMATO;
		this.supplements = new HashSet<Addable>();
		this.stringSupplements = new HashSet<String>();
	}

	public CustomPizza(String pictureUrl) throws URLException {
		super(MINIMUM_PRIZE_CUSTOM_PIZZA, pictureUrl);

		this.dough = Dough.TRADITIONAL;
		this.size = Size.MEDIUM;
		this.sauce = PizzaSauce.TOMATO;
		this.supplements = new HashSet<Addable>();
	}

	public void addSupliment(Addable supliment) {
		if (this.supplements.size() < MAXIMUM_SUPPLEMENTS) {
			this.supplements.add(supliment);
			this.updatePrice(this.getPrice() + supliment.getPrice() * this.size.getCoefficient());
		}
	}

	public void removeSupliment(Addable supliment) {
		if (!this.supplements.isEmpty()) {
			this.supplements.remove(supliment);
			this.updatePrice(this.getPrice() + (supliment.getPrice() * (-1) * this.size.getCoefficient()));
		}
	}

	public void setSize(String size1) {
		for (Size x : Size.values()) {
			if (x.toString().equalsIgnoreCase(size1)) {
				this.updatePrice(this.getPrice() / this.size.getCoefficient());
				this.size = x;
				this.updatePrice(this.size.getCoefficient() * this.getPrice());
			}
		}
	}

	public void setDough(String dough) {
		for (Dough x : Dough.values()) {
			if (x.toString().equalsIgnoreCase(dough)) {
				this.dough = x;
			}
		}
	}

	public void setPizzaSauce(String sauce) {
		for (PizzaSauce x : PizzaSauce.values()) {
			if (x.toString().equalsIgnoreCase(sauce)) {
				this.sauce = x;
			}
		}
	}

	public Set<Addable> getSupplements() {
		return new HashSet<Addable>(supplements);
	}

	public void setSupplements(Set<String> supplements) {
		if (!this.supplements.isEmpty()) {
			for (Addable x : this.supplements) {
				this.removeSupliment(x);
			}
		}
		if (supplements != null && supplements.size() > 0) {
			for (String x : supplements) {
				x = x.replace(' ', '_');
				Addable addable = Addable.toEnum(x);
				this.addSupliment(addable);
			}
		}
	}
	public Set<String> getStringSupplements() throws InvocationTargetException {
		return this.stringSupplements;
	}

	public void setStringSupplements(Set<String> stringSupplements) {
		this.stringSupplements = stringSupplements;
	}

	public String getStringSize() {
		return stringSize;
	}

	public void setStringSize(String stringSize) {
		this.stringSize = stringSize;
	}

	public String getStringDough() {
		return stringDough;
	}

	public void setStringDough(String stringDough) {
		this.stringDough = stringDough;
	}

	public String getStringPizzaSauce() {
		return stringPizzaSauce;
	}

	public void setStringPizzaSauce(String stringPizzaSauce) {
		this.stringPizzaSauce = stringPizzaSauce;
	}

	public Dough getDough() {
		return dough;
	}

	public Size getSize() {
		return size;
	}

	public PizzaSauce getSauce() {
		return sauce;
	}

	public long getCustomPizza_id() {
		return customPizza_id;
	}

	public void setCustomPizza_id(long customPizza_id) {
		this.customPizza_id = customPizza_id;
	}
}
