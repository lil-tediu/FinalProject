package com.dominos.model.products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dominos.db.DBConnection;
import com.dominos.model.enums.Addable;
import com.dominos.model.enums.Cheese;
import com.dominos.model.enums.Dough;
import com.dominos.model.enums.Meat;
import com.dominos.model.enums.Size;
import com.dominos.model.enums.Spice;
import com.dominos.model.enums.Vegetable;
import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;


@Component
public class ProductDAO implements IProductDAO {

	private static final String GET_PRODUCT_BY_ID_SQL = "SELECT * FROM dominos.product where product_id = ?;";
	private static final String GET_ALL_DRINKS_SQL = "SELECT * FROM dominos.product join dominos.drink on product_product_id = product_id;";
	private static final String GET_ALL_SAUCES_SQL = "SELECT * FROM dominos.product join dominos.sauce on product_product_id = product_id;";
	private static final String GET_ALL_PIZZAS_SQL = "SELECT * FROM dominos.product join dominos.pizza on product_product_id = product_id;";
	private static final String INSERT_NEW_PRODUCT_SQL = "INSERT INTO dominos.product (price, picture_url, name) VALUES(?, ?, ?)";
	private static final String INSERT_NEW_CUSTOM_PIZZA_SQL = "INSERT INTO dominos.custompizza (size, dough, sauce, product_product_id) VALUES(?, ?, ?, ?)";
	private static final String INSERT_CHEESE_NEW_CUSTOM_PIZZA_SQL ="INSERT INTO custompizza_has_cheese VALUES(?, ?);";
	private static final String INSERT_MEAT_NEW_CUSTOM_PIZZA_SQL ="INSERT INTO dominos.custompizza_has_meat VALUES (?, ?);";
	private static final String INSERT_SPICE_NEW_CUSTOM_PIZZA_SQL ="INSERT INTO dominos.custompizza_has_spice VALUES (?, ?);";
	private static final String INSERT_VEGETABLE_NEW_CUSTOM_PIZZA_SQL ="INSERT INTO dominos.custompizza_has_vegetable VALUES (?, ?);";
	
	
	
	//private static ProductDAO instance = null;
	private Set<Product> products;
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private DBConnection db;
	
//	public ProductDAO() throws ClassNotFoundException, SQLException {
//		this.products = new HashSet<Product>();
//		this.connection = db.getConnection();
//	}

//	public static ProductDAO getInstance() throws ClassNotFoundException, SQLException {
//		if (ProductDAO.instance == null) {
//			ProductDAO.instance = new ProductDAO();
//		}
//		return instance;
//	}

	/* (non-Javadoc)
	 * @see com.dominos.model.products.IProductDAO#getProductById(long)
	 */
	@Override
	public Product getProductById(long id) throws ProductException, URLException {
		// for (Product product : this.products) {
		// if (product.getId() == id) {
		// return product;
		// }
		// }

		try {
			PreparedStatement st = this.connection.prepareStatement(GET_PRODUCT_BY_ID_SQL);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Product product = new Product(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4));
				return product;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new ProductException("Product with this id not found!");
	}

//	public void addNewProduct(Product product) {
//		this.products.add(product);
//	}

	/* (non-Javadoc)
	 * @see com.dominos.model.products.IProductDAO#getAllDrinks()
	 */
	@Override
	public Set<Drink> getAllDrinks() throws URLException, ProductException {
		Statement st;
		try {
			st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_DRINKS_SQL);
			Set<Drink> products = new HashSet<Drink>();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					products.add(new Drink(rs.getLong(1),rs.getFloat(2), rs.getString(3), rs.getString(4)));
				}
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ProductException("No drinks");
	}

	/* (non-Javadoc)
	 * @see com.dominos.model.products.IProductDAO#getAllSauces()
	 */
	@Override
	public Set<Sauce> getAllSauces() throws URLException, ProductException {
		Statement st;
		try {
			st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_SAUCES_SQL);

			Set<Sauce> products = new HashSet<Sauce>();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					products.add(new Sauce(rs.getLong(1),rs.getFloat(2), rs.getString(3), rs.getString(4)));
				}
				return products;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new ProductException("No Sauces");
	}

	/* (non-Javadoc)
	 * @see com.dominos.model.products.IProductDAO#getAllPizzas()
	 */
	@Override
	public Set<Pizza> getAllPizzas() throws URLException, ProductException {
		Statement st;
		try {
			st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_PIZZAS_SQL);

			Set<Pizza> products = new HashSet<Pizza>();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					 Size size =  Size.valueOf(rs.getString(6));
					 Dough dough = Dough.valueOf(rs.getString(7));
					products.add(new Pizza(rs.getLong(1), rs.getFloat(2), rs.getString(3), rs.getString(4), dough, size,rs.getString(8)));
				}
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ProductException("No pizzas");
	}
	
	
	/* (non-Javadoc)
	 * @see com.dominos.model.products.IProductDAO#addNewCustomPizza(com.dominos.model.products.CustomPizza)
	 */
	@Override
	public void addNewCustomPizza(CustomPizza customPizza) {
		PreparedStatement ps;
		 try {
			this.connection.setAutoCommit(false);
			ps = this.connection.prepareStatement( INSERT_NEW_PRODUCT_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setFloat(1, customPizza.getPrice());
			ps.setString(2, customPizza.getPictureUrl());
			ps.setString(3, customPizza.getName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			customPizza.setId(id);
			
			PreparedStatement psts = this.connection.prepareStatement(INSERT_NEW_CUSTOM_PIZZA_SQL, Statement.RETURN_GENERATED_KEYS);
			psts.setString(1, customPizza.getSize().name());
			psts.setString(2, customPizza.getDough().name());
			psts.setString(3, customPizza.getSauce().name());
			psts.setLong(4, customPizza.getId());
			psts.executeUpdate();
			ResultSet resultSet = psts.getGeneratedKeys();
			resultSet.next();
			long customPizzaId = resultSet.getLong(1);
			customPizza.setCustomPizza_id(customPizzaId);
			
			for (Addable addable : customPizza.getSupplements()) {
				if (addable instanceof Cheese) {
					PreparedStatement prepared = this.connection.prepareStatement(INSERT_CHEESE_NEW_CUSTOM_PIZZA_SQL);
					prepared.setLong(1,  customPizza.getCustomPizza_id());
					prepared.setInt(2, addable.getId());
					prepared.executeUpdate();
				}
				if (addable instanceof Meat) {
					PreparedStatement prepared = this.connection.prepareStatement(INSERT_MEAT_NEW_CUSTOM_PIZZA_SQL);
					prepared.setLong(1,  customPizza.getCustomPizza_id());
					prepared.setInt(2, addable.getId());
					prepared.executeUpdate();
				}
				if (addable instanceof Spice) {
					PreparedStatement prepared = this.connection.prepareStatement(INSERT_SPICE_NEW_CUSTOM_PIZZA_SQL);
					prepared.setLong(1,  customPizza.getCustomPizza_id());
					prepared.setInt(2, addable.getId());
					prepared.executeUpdate();
				}
				if (addable instanceof Vegetable) {
					PreparedStatement	prepared = this.connection.prepareStatement(INSERT_VEGETABLE_NEW_CUSTOM_PIZZA_SQL);
					prepared.setLong(1, customPizza.getCustomPizza_id());
					prepared.setInt(2, addable.getId());
					prepared.executeUpdate();
				}
				this.connection.commit();
			}
		} catch (SQLException e) {
			try {
				this.connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				this.connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
		
//	public CustomPizza createCustomPizza() throws URLException {
//		return new CustomPizza("img/custmPizza");
//	}
	
	
	
	// public CustomPizza createPizza() throws URLException {
	// CustomPizza pizza = new CustomPizza("");
	//
	// return pizza;
	// }

	// public Set<Product> getProducts() {
	// Set<Product> products = Collections.unmodifiableSet(this.products);
	// return products;
	// }
	//
	// public Set<Pizza> getAllPizzas() {
	// Set<Pizza> pizzas = new HashSet<Pizza>();
	// for (Product x : this.getProducts()) {
	// if (x instanceof Pizza ) {
	// pizzas.add((Pizza) x);
	// }
	// }
	// return pizzas;
	// }
	//
	// public Set<Drink> getAllDrinks() {
	// Set<Drink> drinks = new HashSet<Drink>();
	// for (Product x : this.getProducts()) {
	// if (x instanceof Drink ) {
	// drinks.add((Drink) x);
	// }
	// }
	// return drinks;
	// }
	//
	// public Set<Sauce> getAllSauces() {
	// Set<Sauce> sauces = new HashSet<Sauce>();
	// for (Product x : this.getProducts()) {
	// if (x instanceof Sauce ) {
	// sauces.add((Sauce) x);
	// }
	// }
	// return sauces;
	// }
}
