package com.dominos.model.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.dominos.model.address.Address;
import com.dominos.model.order.Order;

public class User {
	private long id;
	@Pattern(regexp="[A-Za-z]{1,128}", message="Invalid input.")
	private String firstName;
	@Pattern(regexp="[A-Za-z]{1,128}", message="Invalid input.")
	private String lastName;
	@Size(min=6,max=50 , message="Invalid password. Length must be more than 6 characters.")
	private String password;
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Please enter a valid email.")
	private String email;
	private String pictureUrl;
	private Set<Order> orders;
	private Set<Address>addresses;

	public User() {
		this.orders = new HashSet<Order>();
		this.addresses=new HashSet<Address>();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", pictureUrl=" + pictureUrl + ", orders=" + orders + ", addresses=" + addresses
				+ "]";
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


	public Set<Order> getOrders() {
		return new HashSet<Order>(this.orders);
	}


	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Set<Address> getAddresses() {
		return Collections.unmodifiableSet(addresses);
	}
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}
	public void addAddress(Address address) {
		this.addresses.add(address);
	}
}
