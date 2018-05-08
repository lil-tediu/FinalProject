package com.dominos.model.products;

import java.util.Set;

import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;

public interface IProductDAO {

	Product getProductById(long id) throws ProductException, URLException;

	Set<Drink> getAllDrinks() throws URLException, ProductException;

	Set<Sauce> getAllSauces() throws URLException, ProductException;

	Set<Pizza> getAllPizzas() throws URLException, ProductException;

	void addNewCustomPizza(CustomPizza customPizza);

}