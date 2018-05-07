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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dominos.model.address.Address;
import com.dominos.model.address.AddressDao;
import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;
import com.dominos.model.products.Drink;
import com.dominos.model.products.ProductDAO;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Controller
public class AddressesController {
	@Autowired
	private UserDao dao;

	@Autowired
	private AddressDao ad;

	@RequestMapping(value = "/viewaddresses", method = RequestMethod.GET)
	public String allAddress(Model model, HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			 return "redirect:/index";
		}

//		try {
//			ad = AddressDao.getInstance();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		HashSet<Address> addresses = new HashSet<>();
		try {
			User u = (User)s.getAttribute("loggedUser");
			addresses = ad.getAddressOfUser(u);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("addresses", addresses);
		System.out.println(addresses.size());
		for (Address address : addresses) {
			System.out.println(address.getAddress());
		}
		return "viewaddresses";
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public String addAddress(Model model,  HttpSession s) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			 return "redirect:/index";
		}

		Address a = new Address();
		User loggedUser = (User) s.getAttribute("loggedUser");
		if (loggedUser != null) {
			System.out.println("User exist");
		} else {
			System.out.println("User does nott exist");
		}
		model.addAttribute("address", a);
	//	model.addAttribute(loggedUser, arg1)
		return "addresses";
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.POST)
	public String saveAddress(@ModelAttribute Address readyAddress,HttpSession s,HttpServletRequest request) {
		if (s==null || s.getAttribute("loggedUser")==null) {
			 return "redirect:/index";
		}
		
	//	String adr = readyAddress.getAddress();
		User loggedUser = (User) s.getAttribute("loggedUser");
		try {
			String addr = request.getParameter("address");
			System.out.println(addr);
			String city = request.getParameter("city");
			System.out.println(city);
			String state = request.getParameter("state");
			System.out.println(state);
			
			String zip=request.getParameter("zip");
			System.out.println(zip);
			StringBuilder adr=new StringBuilder();
			adr.append(addr);
			adr.append(" ,");
			adr.append(city);
			adr.append(" ,");
			adr.append(state);
			adr.append(" ,");
			adr.append(zip);
			
			System.out.println(adr.toString());
			
			readyAddress.setAddress(adr.toString());
			readyAddress.setUser(loggedUser);
			System.out.println("in post=user " + loggedUser);
			ad.insertAddressForUser(readyAddress);
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:index.html";

		}
		return "redirect:pizzas";
	}
}