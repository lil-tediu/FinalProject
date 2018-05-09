package com.dominos.model.address;

import com.dominos.model.user.User;

public class Address {
private long address_id;
private String address;
private User user;

//public Address(String address,User user) {
//	this.address=address;
//	this.user=user;
//}

//public Address(long address_id,String address,User user) {
//	this(address,user);
//	this.address_id=address_id;
//}

public long getAddress_id() {
	return address_id;
}

public void setAddress_id(long address_id) {
	this.address_id = address_id;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

}
