package com.dominos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class DBConnection {
	private final Connection connection;
	
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "localhost";
	private static final String DB_SCHEMA = "dominos";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "1914";
	
	
	//private static DBConnection instance = null;
	
	public DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(
				"jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_SCHEMA,
				DB_USER, DB_PASS);
	}
	
//	public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
//		if (DBConnection.instance == null) {
//			DBConnection.instance = new DBConnection();
//		}
//		return instance;
//	}
	
	@Bean
	public Connection getConnection() {
		return connection;
	}
}