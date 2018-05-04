package com.dominos.controller;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dominos.model.address.Address;
import com.dominos.model.address.AddressDao;
import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;
import com.dominos.model.order.Order;
import com.dominos.model.order.OrderDao;
import com.dominos.model.products.Product;
import com.dominos.model.products.ProductDAO;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Controller
public class CartController {
	
	@Autowired
	private UserDao dao;

	@Autowired
	private OrderDao odao;
	
	@Autowired
	private ProductDAO pdao;
	
	@Autowired
	private AddressDao adao;

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String productsInCart(Model model, HttpServletRequest request ) {
		if (request.getSession(false)==null || request.getSession().getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) request.getSession().getAttribute("loggedUser");
			Order order = odao.getActiveOrderForUser(user);
			float price = (float) Order.calculatePriceForCart(order.getProducts());
			model.addAttribute("order", order);
			model.addAttribute("price", price);
			return "cart";
	}
	
	
	
	
	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String removeProductFromCart(Model model, HttpServletRequest request) {
		if (request.getSession(false)==null || request.getSession().getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) request.getSession().getAttribute("loggedUser");
			Order order = odao.getActiveOrderForUser(user);
			long id = Long.parseLong(request.getParameter("chosen"));
			try {
				Product product = pdao.getProductById(id);
				order.removeProduct(product);
				float price = (float) Order.calculatePriceForCart(order.getProducts());
				model.addAttribute("order", order);
				model.addAttribute("price", price);
			} catch (ProductException | URLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "cart";
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.GET)
	public String submitOrder(Model model, HttpServletRequest request) {
		if (request.getSession(false)==null || request.getSession().getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) request.getSession().getAttribute("loggedUser");
			try {
				Set<Address> addresses = adao.getAddressOfUser(user);
				model.addAttribute("adresses", addresses);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "chooseAddress";
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public String submitAddressForOrder(Model model, HttpServletRequest request) {
		if (request.getSession(false)==null || request.getSession().getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) request.getSession().getAttribute("loggedUser");
			long addres_id = Long.parseLong(request.getParameter("chosenAddress"));
			Order order = odao.getActiveOrderForUser(user);
			Order.calculatePriceForCart(order.getProducts());
			order.setDelivered(true);
			System.out.println(addres_id);
			Address address = adao.getAddresById(addres_id);
			
			
			try {
				order.setAddres(address);
				odao.insertOrderForUser(order);
				odao.insertProductsFromOrder(order.getId(), order.getProducts());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "addedSuccessfully";
	}
	
	
}