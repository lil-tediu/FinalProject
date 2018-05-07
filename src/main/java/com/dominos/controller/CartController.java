package com.dominos.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	public String productsInCart(Model model, HttpSession s ) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) s.getAttribute("loggedUser");
			Order order = odao.getActiveOrderForUser(user);
			float price = (float) Order.calculatePriceForCart(order.getProducts());
			model.addAttribute("order", order);
			model.addAttribute("price", price);
			return "cart";
	}
	
	
	
	
	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String removeProductFromCart(Model model,HttpSession s,HttpServletRequest request) {
		if (s==null ||s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) s.getAttribute("loggedUser");
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
				return "error";
			}
			return "cart";
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.GET)
	public String submitOrder(Model model, HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User)s.getAttribute("loggedUser");
			try {
				Set<Address> addresses = adao.getAddressOfUser(user);
				model.addAttribute("adresses", addresses);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}
			return "chooseAddress";
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public String submitAddressForOrder(Model model, HttpSession s,HttpServletRequest request) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) s.getAttribute("loggedUser");
			long addres_id = Long.parseLong(request.getParameter("chosenAddress"));
			Order order = odao.getActiveOrderForUser(user);
			order.setPrice((float) Order.calculatePriceForCart(order.getProducts()));
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
				return "error";
			}
			return "addedSuccessfully";
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String viewOrders(Model model, HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
			User user = (User) s.getAttribute("loggedUser");
			try {
				Set<Order> orders = odao.getOrdersForUser(user.getId());
				Map<Order, String> ordersAndAddresses = new TreeMap<Order, String>(new Comparator<Order>() {
					@Override
					public int compare(Order o1, Order o2) {
						return o2.getDatetime().compareTo(o1.getDatetime());
					}
				});
				
				for (Order order : orders) {
					ordersAndAddresses.put(order, order.getAddres().getAddress());
				}
				model.addAttribute("ordersAndAddresses", ordersAndAddresses);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}
			return "viewOrders";
	}
	
	
}