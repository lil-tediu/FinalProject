package com.dominos.model.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dominos.db.DBConnection;
import com.dominos.model.exceptions.AddressException;
import com.dominos.model.order.Order;
import com.dominos.model.products.Product;
import com.dominos.model.products.ProductDAO;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Component
public class AddressDao {

	private static final String GET_ALL_ADDRESSES_OF_USER = "select * from " + "address a\r\n"
			+ "join user u\r\n" + "on (a.user_id=u.user_id) where u.user_id=?";

	private static final String GET_ADDRESS_OF_ORDER = "select a.address from orders o\r\n" + "join address a\r\n"
			+ "on (a.address_id=o.address_id) where o.order_id=?";

	private static final String INSERT_ADDRESS_FOR_USER = "INSERT INTO address (address,user_id) \r\n"
			+ "VALUES (?,?);";
	// insert address for user
	
	
	private static final String GET_ADDRESS_BY_ID = "SELECT * FROM dominos.address where address_id = ?;";
	private static AddressDao instance = null;

	@Autowired
	private Connection con;
	
	@Autowired
	private UserDao dao;

	@Autowired
	private DBConnection db = new DBConnection();

	private AddressDao() throws ClassNotFoundException, SQLException {
		this.con = db.getConnection();
	}
	public static AddressDao getInstance() throws ClassNotFoundException, SQLException {
		if (AddressDao.instance == null) {
			AddressDao.instance = new AddressDao();
		}
		return instance;
	}

	public HashSet<Address> getAddressOfUser(User u) throws ClassNotFoundException, SQLException {
		// Connection con = DBconnection.getConnection();
		PreparedStatement ps = con.prepareStatement(GET_ALL_ADDRESSES_OF_USER);
		ps.setLong(1, u.getId());

		ResultSet rs = ps.executeQuery();
		HashSet<Address> addresses = new HashSet<>();
		while (rs.next()) {
			Address address = new Address();
			address.setAddress(rs.getString("address"));
			address.setUser(u);
			address.setAddress_id(rs.getLong(1));
			addresses.add(address);
		}
		return addresses;
	}

	public Address getAddressOfOrder(Order o) throws ClassNotFoundException, SQLException, AddressException {
		ResultSet rs = null;
		// Connection con = DBconnection.getConnection();
		try (PreparedStatement ps = con.prepareStatement(GET_ADDRESS_OF_ORDER);) {
			ps.setLong(1, o.getId());
			Address adr = new Address();

			rs = ps.executeQuery();
			if (!rs.next()) {

				return new Address();
			}
			adr.setAddress(rs.getString("a.address"));
			adr.setUser(o.getUser());
			return adr;

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public void insertAddressForUser(Address address) throws SQLException {
		// Connection con = DBconnection.getConnection();
		String query = INSERT_ADDRESS_FOR_USER;
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, address.getAddress());
			ps.setLong(2, address.getUser().getId());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			address.setAddress_id(rs.getInt(1));

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	public Address getAddresById(long id) {
		Address address = new Address();
		try {
			PreparedStatement ps =  con.prepareStatement(GET_ADDRESS_BY_ID);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			long address_id = rs.getLong(1);
			String address1 = rs.getString(2);
			long user_id = rs.getLong(3);
			
			address.setAddress(address1);
			address.setUser(dao.getUserByID(user_id));
			address.setAddress_id(address_id);
			return address;
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
		
		
	}
}
