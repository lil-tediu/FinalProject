package com.dominos.model.address;

import java.sql.SQLException;
import java.util.HashSet;

import com.dominos.model.exceptions.AddressException;
import com.dominos.model.order.Order;
import com.dominos.model.user.User;

public interface IAddressDAO {

	HashSet<Address> getAddressOfUser(User u) throws ClassNotFoundException, SQLException;

	Address getAddressOfOrder(Order o) throws ClassNotFoundException, SQLException, AddressException;

	void insertAddressForUser(Address address) throws SQLException;

	Address getAddresById(long id);

	void deleteAddress(long userId, long addressId);

}