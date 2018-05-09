package com.dominos.controller;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;
import com.dominos.model.order.Order;
import com.dominos.model.order.OrderDao;
import com.dominos.model.user.User;
import com.dominos.model.products.Drink;
import com.dominos.model.products.IProductDAO;
import com.dominos.model.products.Pizza;
import com.dominos.model.products.Product;
import com.dominos.model.products.Sauce;

@Controller
public class ProductController {
	
	@Autowired
	private IProductDAO dao;
	@Autowired
	private OrderDao orderDao;
	
	@RequestMapping(value="/drinks",method = RequestMethod.GET)
	public String allDrinks(Model model, HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			try {
				Set<Drink> drinks = dao.getAllDrinks();
				model.addAttribute("drinks", drinks);
			} catch (URLException | ProductException e) {
				e.printStackTrace();
				return "error";
			}
			return "AllDrinksNotReg";
		}
		
		try {
			Set<Drink> drinks = dao.getAllDrinks();
			model.addAttribute("drinks", drinks);
			User user = ((User) s.getAttribute("loggedUser"));
			Order order = orderDao.getActiveOrderForUser(user);
			order.setUser(user);
			user.addOrder(order);
			model.addAttribute("order", order);
		} catch (URLException | ProductException e) {
			e.printStackTrace();
			return "error";

		}
		return "AllDrinks";
	}
	
	
	@RequestMapping(value="/pizzas",method = RequestMethod.GET)
	public String allPizzas(Model model, HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			try {
				Set<Pizza> pizzas = dao.getAllPizzas();
				model.addAttribute("pizzas", pizzas);
			} catch (URLException | ProductException e) {
				e.printStackTrace();
				return "error";
			}
			return "pizzasNotReg";
		}
		
		try {
			Set<Pizza> pizzas = dao.getAllPizzas();
			model.addAttribute("pizzas", pizzas);
			User user = ((User) s.getAttribute("loggedUser"));
			Order order = orderDao.getActiveOrderForUser(user);
			order.setUser(user);
			user.addOrder(order);
			model.addAttribute("order", order);
		} catch (URLException | ProductException e) {
			e.printStackTrace();
			return "error";
		}
		return "pizzas";
	}
	
	
	@RequestMapping(value="/sauces",method = RequestMethod.GET)
	public String allSauces(Model model, HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			try {
				Set<Sauce> sauces = dao.getAllSauces();
				model.addAttribute("sauces", sauces);
			} catch (URLException | ProductException e) {
				e.printStackTrace();
				return "error";
			}
			return "saucesNotReg";
		}
		
		try {
			Set<Sauce> sauces = dao.getAllSauces();
			model.addAttribute("sauces", sauces);
			User user = ((User) s.getAttribute("loggedUser"));
			Order order = orderDao.getActiveOrderForUser(user);
			order.setUser(user);
			user.addOrder(order);
			model.addAttribute("order", order);
			
		} catch (URLException | ProductException e) {
			e.printStackTrace();
			return "error";
		}
		return "sauces";
	}
	
	@RequestMapping(value="/pizzas",method = RequestMethod.POST)
	public String allPizzasAdd(Model model, HttpSession s,HttpServletRequest request) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
		
		try {
			long productId = Long.parseLong(request.getParameter("chosen"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			Product product = dao.getProductById(productId);
			User user = ((User) s.getAttribute("loggedUser"));
			Order order = orderDao.getActiveOrderForUser(user);
			order.addProduct(product, quantity);
			float price = (float) Order.calculatePriceForCart(order.getProducts());
			model.addAttribute("price", price);
			model.addAttribute("order", order);
		} catch (URLException | ProductException e) {
			e.printStackTrace();
			return "error";
		}
		catch (NumberFormatException e) {
			return "redirect:pizzas";
		}
		return "redirect:cart";
	}
	
	
	
	@RequestMapping(value="/drinks",method = RequestMethod.POST)
	public String allDrinksAdd(Model model, HttpSession s,HttpServletRequest request) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			 return "redirect:/index";
		}
		try {
			User user = ((User) s.getAttribute("loggedUser"));
			Order order = orderDao.getActiveOrderForUser(user);
			long productId = Long.parseLong(request.getParameter("chosen"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			Product product = dao.getProductById(productId);
			order.addProduct(product, quantity);
	
			float price = (float) Order.calculatePriceForCart(order.getProducts());
			model.addAttribute("price", price);
			model.addAttribute("order", order);
		} catch (URLException | ProductException e) {
			// TODO Auto-generated catch block
			return "error";
		}
		catch (NumberFormatException e) {
			return "redirect:drinks";
		}
		return "redirect:cart";
	}
	
	@RequestMapping(value="/sauces",method = RequestMethod.POST)
	public String allSaucesAdd(Model model,HttpSession s, HttpServletRequest request) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			 return "redirect:/index";
		}
		try {
			User user = ((User) s.getAttribute("loggedUser"));
			Order order = orderDao.getActiveOrderForUser(user);
			long productId = Long.parseLong(request.getParameter("chosen"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			Product product = dao.getProductById(productId);
			order.addProduct(product, quantity);
			
	        float price = (float) Order.calculatePriceForCart(order.getProducts());
			model.addAttribute("price", price);
			model.addAttribute("order", order);
		} catch (URLException | ProductException e) {
			e.printStackTrace();
			return "error";
		}
		catch (NumberFormatException e) {
			return "redirect:sauces";
		}
		return "cart";
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request) {
		request.getSession().invalidate();
			return "redirect:/index";
		}
}
