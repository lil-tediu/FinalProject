package com.dominos.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dominos.model.address.Address;
import com.dominos.model.address.IAddressDAO;
import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;
import com.dominos.model.order.IOrderDAO;
import com.dominos.model.order.Order;
import com.dominos.model.order.OrderDao;
import com.dominos.model.products.Drink;
import com.dominos.model.products.Product;
import com.dominos.model.products.ProductDAO;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Controller
public class AddressesController {

	@Autowired
	private IAddressDAO ad;

	@Autowired
	private IOrderDAO od;

	@RequestMapping(value = "/viewaddresses", method = RequestMethod.GET)
	public String allAddress(Model model, HttpSession s) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}

		HashSet<Address> addresses = new HashSet<>();
		try {
			User u = (User) s.getAttribute("loggedUser");
			addresses = ad.getAddressOfUser(u);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";

		}
		model.addAttribute("addresses", addresses);
		return "viewaddresses";
	}

	@RequestMapping(value = "viewaddresses", method = RequestMethod.POST)
	public String removeAddress(Model model, HttpSession s, HttpServletRequest request) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}
		User user = (User) s.getAttribute("loggedUser");
		System.out.println(user);
		System.out.println(user.getId());
		System.out.println(user.getEmail());
		long id = Long.parseLong(request.getParameter("chosen"));
		try {
			od.deleteOrderOnAddress(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		try {
			ad.deleteAddress(user.getId(), id);
			return "successRemoveAddress";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public String addAddress(Model model, HttpSession s) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}

		Address a = new Address();
		model.addAttribute("address", a);
		return "addresses";
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.POST)
	public String saveAddress(@ModelAttribute Address readyAddress, HttpSession s, HttpServletRequest request,
			ModelMap map) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}

		User loggedUser = (User) s.getAttribute("loggedUser");
		try {
			String addr = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");

			String zip = request.getParameter("zip");
			StringBuilder adr = new StringBuilder();
			adr.append(addr);
			adr.append(" ,");
			adr.append(city);
			adr.append(" ,");
			adr.append(state);
			adr.append(" ,");
			adr.append(zip);
			String address = adr.toString();
			if (ad.hasSuchAddress(address,loggedUser.getId())) {
				String message = "Sorry, this address already exist";
				map.put("error", message);
				return "addresses";
			} else {
				readyAddress.setAddress(address);
				readyAddress.setUser(loggedUser);
				loggedUser.addAddress(readyAddress);
				ad.insertAddressForUser(readyAddress);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return "error";
		}
		return "redirect:viewaddresses";
	}
}