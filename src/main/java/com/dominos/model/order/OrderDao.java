package com.dominos.model.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dominos.db.DBConnection;
import com.dominos.model.address.Address;
import com.dominos.model.address.IAddressDAO;
import com.dominos.model.exceptions.AddressException;
import com.dominos.model.products.Product;
import com.dominos.model.user.IUserDAO;
import com.dominos.model.user.User;

@Component
public class OrderDao implements IOrderDAO {

	@Autowired
	DBConnection DBconnection;

	@Autowired
	IUserDAO userDao;
	
	@Autowired
	IAddressDAO addressDao;

	@Autowired
	Connection con;

	private static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?;";
	private static final String INSERT_PRODUCT_IN_ORDER = "INSERT INTO order_has_product (product_id, order_id, quantity) \r\n"
			+ "VALUES (?,?,?);";
	private static final String INSERT_ORDER_FOR_USER = "INSERT INTO orders ( user_id, date , price, address_id,isDelivered) "
			+ "VALUES (?,?,?,?,?);";

	private static final String GET_ALL_USER_ORDERS = "select o.isDelivered, o.price, o.order_id,o.date,a.address\r\n"
			+ "from user u \r\n" + "join orders o \r\n" + "on(u.user_id=o.user_id) \r\n" + "join address a\r\n"
			+ "on(a.address_id=o.address_id)\r\n" + "where u.user_id=? \r\n" + "ORDER BY date";

	private static final String GET_PRODUCTS_OF_ORDER_BY_ID = "SELECT p.product_id AS product_id,  p.name , p.price AS price, p.picture_url ,op.quantity \r\n"
			+ "					FROM product AS p  \r\n"
			+ "					JOIN order_has_product AS op USING (product_id)\r\n"
			+ "                    WHERE op.order_id = ? ORDER BY op.quantity";
	private static final String GET_ACTIVE_ORDER_FOR_USER = "SELECT * FROM dominos.orders AS o where o.user_id = ? AND isDelivered = false;";

	private static final String GET_ALL_ORDERS_ON_ADDRESS = "SELECT order_id from orders join address \r\n" + 
			"on orders.address_id = address.address_id \r\n" + 
			"where address.address_id=?;";
	
	private static final String SET_ADDRESS_OF_ORDERS_NULL="UPDATE orders SET address_id=null WHERE order_id =?;";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dominos.model.db.IOrderDAO#getOrdersForUser(long)
	 */

	@Override
	public TreeSet<Order> getOrdersForUser(long user_id) throws ClassNotFoundException, SQLException {
		// Connection con = DBconnection.getConnection();
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(GET_ALL_USER_ORDERS);) {
			ps.setLong(1, user_id);
			rs = ps.executeQuery();

			TreeSet<Order> orders = new TreeSet<>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o2.getDatetime().compareTo(o1.getDatetime());
				}
			});
			Order order = null;
			while (rs.next()) {

				HashMap<Product, Integer> products = this.getProductsForOrder(rs.getLong("order_id"));
				// User user=userDao.getUserByID(user_id);
				LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
				Address a = new Address();
				a.setAddress(rs.getString("a.address"));
				// a.setUser(user);
				order = new Order();
				order.setId(rs.getLong("order_id"));

				order.setDatetime(date);
				// order.setUser(user);
				order.setProducts(products);
				order.setPrice(rs.getFloat("price"));
				order.setAddres(a);
				order.setDelivered(rs.getBoolean("isDelivered"));

				orders.add(order);
			}
			return orders;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dominos.model.db.IOrderDAO#getProductsForOrder(long)
	 */
	@Override
	public HashMap<Product, Integer> getProductsForOrder(long orderId) throws SQLException {
		// Connection con = DBconnection.getConnection();
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(GET_PRODUCTS_OF_ORDER_BY_ID);) {
			ps.setLong(1, orderId);
			rs = ps.executeQuery();
			HashMap<Product, Integer> productsForOrder = new HashMap<Product, Integer>();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("product_id"));
				product.setName(rs.getString("p.name"));
				product.setPrice(rs.getFloat("price"));
				product.setPictureUrl(rs.getString("p.picture_url"));
				productsForOrder.put(product, rs.getInt("op.quantity"));
			}
			return productsForOrder;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dominos.model.db.IOrderDAO#insertOrderForUser(com.dominos.model.order.
	 * Order)
	 */
	@Override
	public void insertOrderForUser(Order order) throws SQLException {
		// Connection con = DBconnection.getConnection();
		String query = INSERT_ORDER_FOR_USER;
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setLong(1, order.getUser().getId());
			ps.setString(2, (order.getDatetime()).toString());
			ps.setFloat(3, order.getPrice());
			ps.setLong(4, order.getAddres().getAddress_id());
			ps.setBoolean(5, order.isDelivered());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			order.setId(rs.getInt(1));

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dominos.model.db.IOrderDAO#insertProductsFromOrder(long,
	 * java.util.HashMap)
	 */
	@Override
	public void insertProductsFromOrder(long orderId, HashMap<Product, Integer> cart) throws SQLException {
		// Connection con = DBconnection.getConnection();

		for (Entry<Product, Integer> entry : cart.entrySet()) {
			Product product = entry.getKey();
			int quantity = (int) entry.getValue();

			try (PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT_IN_ORDER);) {
				ps.setLong(1, product.getId());
				ps.setLong(2, orderId);
				ps.setInt(3, quantity);
				ps.executeUpdate();
			} finally {
			}
		}
	}

	@Override
	public Order getActiveOrderForUser(User user) {

		for (Order order : user.getOrders()) {
			if (!order.isDelivered()) {
				return order;
			}
		}
		Order order = new Order();
		order.setUser(user);
		return order;

	}

	public void deleteOrderOnAddress(long address_id) {
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(GET_ALL_ORDERS_ON_ADDRESS);) {
			ps.setLong(1, address_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				
				long order_id=rs.getLong(1);
				System.out.println("ord id "+order_id);
				
				PreparedStatement ps1=con.prepareStatement(SET_ADDRESS_OF_ORDERS_NULL);
				ps1.setLong(1,order_id);
				int res=ps1.executeUpdate();
				System.out.println("result "+res);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Order getOrderById(long id) throws SQLException, ClassNotFoundException, AddressException {
		ResultSet rs = null;
		try (PreparedStatement ps = con.prepareStatement(GET_ORDER_BY_ID)) {
			ps.setLong(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong(1));
				order.setDatetime((rs.getTimestamp((2)).toLocalDateTime()));
				order.setUser(userDao.getUserByID(rs.getLong(3)));
				order.setAddres(addressDao.getAddresById(rs.getLong(4)));
				order.setPrice(rs.getFloat(5));
				order.setDelivered(true);
				return order;
			}
		}   
		throw new AddressException();
	}

}
