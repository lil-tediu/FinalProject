package com.dominos.model.products;

import java.math.BigDecimal;

import com.dominos.model.exceptions.URLException;

public class Product {
	protected long id;
	private float price;
	private String pictureUrl;
	private String name;
	
	
	
	public Product() {
		this.pictureUrl = "img/custmPizza.png";
		this.price = 6.50f;
	}
	
	public Product(float price, String pictureUrl) throws URLException {
		this.price = price;
//		if (pictureUrl!=null && pictureUrl.trim().length()>0)
			this.pictureUrl = pictureUrl;
//		else {
//			throw new URLException("Invalid URL!!");
//		}
	}


	public Product(long id, float price, String pictureUrl, String name) throws URLException {
		this(price, pictureUrl);
		if (name!=null && name.trim().length()>0) {
			this.name=name;
		}
		this.id = id;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}



	public float getPrice() {
		BigDecimal bd = new BigDecimal(this.price);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}



	public String getPictureUrl() {
		return pictureUrl;
	}
	
	public void updatePrice(float price) {
		
		this.price =price;
	}


	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
