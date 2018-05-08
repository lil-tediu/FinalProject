package com.dominos.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dominos.db.DBConnection;
import com.dominos.model.address.Address;
import com.dominos.model.address.IAddressDAO;
import com.dominos.model.order.Order;
import com.dominos.model.order.OrderDao;
//import com.dominos.tests.Encrypter;

@Component
public class UserDao implements IUserDAO {

	private static final String IS_USER_EXCIST = "SELECT count(*) as count FROM user WHERE e_mail = ? AND password = ?";

	private static final String REGISTER_USER_SQL = "INSERT INTO user (firstName, lastName,password, e_mail, picture_url) VALUES (?, ?, ?, ?,?)";

	private static final String GET_USER_BY_MAIL = "SELECT user_id, firstName, lastName,password,e_mail, picture_url FROM user WHERE e_mail = ?";
	// try
	private static final String UPDATE_USER = "UPDATE user SET firstName = ?, lastName = ?, e_mail = ?, password = ?, picture_url= ? WHERE user_id= ?;";
	// try
	private static final String GET_USER_BY_ID = "SELECT e_mail AS email, firstName , lastName, password, picture_url FROM user WHERE user_id = ?";
	
	private static final String INSERT_ADDRESS_FOR_USER = "INSERT INTO address ( address, user_id) \r\n" + 
			"					VALUES (?,?);";
	@Autowired
	private DBConnection db;


    @Autowired
	private OrderDao od;
	
	@Autowired 
	private IAddressDAO ad;
	
	@Autowired
	private Connection con;
	
	
	public void insertAddressForUser(Address address) throws SQLException {
		//Connection con = db.getConnection();
		
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(INSERT_ADDRESS_FOR_USER, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, address.getAddress());
			ps.setLong(2, address.getUser().getId());
			

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			address.setAddress_id(rs.getLong(1));

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
	public  void register(User u) throws SQLException, ClassNotFoundException {
		//Connection con = db.getConnection();
		ResultSet rs = null;
		try (PreparedStatement ps = con.prepareStatement(REGISTER_USER_SQL, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getPassword());
			ps.setString(4, u.getEmail());
			ps.setString(5, u.getPictureUrl());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			u.setId(rs.getLong(1));
			System.out.println("User " + u.getFirstName() + " has id " + u.getId());

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public  boolean existsUser(String e_mail, String password) throws SQLException, ClassNotFoundException {
		//Connection con = db.getConnection();
		ResultSet rs = null;
		try (PreparedStatement ps = con.prepareStatement(IS_USER_EXCIST);) {

			ps.setString(1, e_mail);
			ps.setString(2, password);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt("count") > 0;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// toDO
	// public static boolean isValidEmailAddress(String email) {
	// boolean result = true;
	// try {
	// InternetAddress emailAddr = new InternetAddress(email);
	// emailAddr.validate();
	// } catch (AddressException ex) {
	// result = false;
	// }
	// return result;
	// }

	public  User getUser(String e_mail) throws SQLException, ClassNotFoundException {
	//	Connection con = db.getConnection();

		ResultSet rs = null;
		try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_MAIL);) {
			ps.setString(1, e_mail);
			rs = ps.executeQuery();
			User u = null;
			if (!rs.next()) {
				return new User();
			}
			u = new User();
			u.setId(rs.getLong("user_id"));
			u.setFirstName(rs.getString("firstName"));
			u.setLastName(rs.getString("lastName"));
			u.setPassword(rs.getString("password"));
			u.setEmail(rs.getString(5));
			u.setPictureUrl(rs.getString("picture_url"));

////			 TreeSet<Order> orders = od.getOrdersForUser(u.getId());
//			 HashSet<Address> addresses = ad.getAddressOfUser(u);
//
//		    u.setOrders(orders);
//			u.setAddresses(addresses);

			return u;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dominos.model.db.IUserDAO#updateUser(com.dominos.model.user.User)
	 */
	@Override
	public boolean updateUser(User user) throws SQLException {
		//Connection con = db.getConnection();

		try (PreparedStatement stmt = con.prepareStatement(UPDATE_USER);) {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getPictureUrl());
			stmt.setLong(6, user.getId());
			return stmt.executeUpdate() == 1;
		} finally {
		}
	}

	/* (non-Javadoc)
	 * @see com.dominos.model.db.IUserDAO#getUserByID(long)
	 */
	@Override
	public User getUserByID(long id) throws SQLException, ClassNotFoundException {
	//	Connection con = db.getConnection();

		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(GET_USER_BY_ID);) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			rs.next();

			User u = new User();
			u.setId(id);
			u.setFirstName(rs.getString("firstName"));
			u.setLastName(rs.getString("lastName"));
			u.setPassword(rs.getString("password"));
			u.setEmail(rs.getString(5));
			u.setPictureUrl(rs.getString("picture_url"));

			TreeSet<Order> orders = od.getOrdersForUser(u.getId());
			HashSet<Address> addresses = ad.getAddressOfUser(u);
			u.setAddresses(addresses);
		    u.setOrders(orders);
			return u;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}
}
