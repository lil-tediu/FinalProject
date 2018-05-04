package com.dominos.model.user;

import java.sql.SQLException;

public interface IUserDAO {

	public  void register(User u) throws SQLException, ClassNotFoundException;
	public  boolean existsUser(String e_mail, String password) throws SQLException, ClassNotFoundException ;
	public  User getUser(String e_mail) throws SQLException, ClassNotFoundException ;

	public boolean updateUser(User user) throws SQLException ;

	public User getUserByID(long id)throws SQLException, ClassNotFoundException ;

}