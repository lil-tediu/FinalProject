package com.dominos.model.order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeSet;

import com.dominos.model.products.Product;
import com.dominos.model.user.User;

public interface IOrderDAO {

	TreeSet<Order> getOrdersForUser(long user_id) throws ClassNotFoundException, SQLException;

	HashMap<Product, Integer> getProductsForOrder(long orderId) throws SQLException;

	void insertOrderForUser(Order order) throws SQLException;

	void insertProductsFromOrder(long orderId, HashMap<Product, Integer> cart) throws SQLException;


	Order getActiveOrderForUser(User user) throws SQLException;
}