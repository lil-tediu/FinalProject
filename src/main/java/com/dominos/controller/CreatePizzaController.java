package com.dominos.controller;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dominos.model.enums.Addable;
import com.dominos.model.enums.Cheese;
import com.dominos.model.enums.Dough;
import com.dominos.model.enums.Meat;
import com.dominos.model.enums.PizzaSauce;
import com.dominos.model.enums.Size;
import com.dominos.model.enums.Spice;
import com.dominos.model.enums.Vegetable;
import com.dominos.model.exceptions.ProductException;
import com.dominos.model.exceptions.URLException;
import com.dominos.model.order.Order;
import com.dominos.model.order.OrderDao;
import com.dominos.model.products.CustomPizza;
import com.dominos.model.products.IProductDAO;
import com.dominos.model.products.Product;
import com.dominos.model.user.User;

@Controller
public class CreatePizzaController {
	
	@Autowired
	private IProductDAO dao;
	
	@Autowired
	private OrderDao odao;
	
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public String createPiiza(Model model, HttpSession ses) throws URLException {
		if (ses==null || ses.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
		Set<String> cheese = new HashSet<String>();
		for (Cheese c : Cheese.values()) {
			String s = c.toString();
			cheese.add(s);
		}
		model.addAttribute("cheese", cheese);
		
		Set<String> meat = new HashSet<String>();
		for (Meat c : Meat.values()) {
			String s = c.toString();
			meat.add(s);
		}
		model.addAttribute("meat", meat);
		
		Set<String> spices = new HashSet<String>();
		for (Spice s : Spice.values()) {
			String s1 = s.toString();
			spices.add(s1);
		}
		model.addAttribute("spices", spices);
		
		Set<String> vegetables = new HashSet<String>();
		for (Vegetable v : Vegetable.values()) {
			String s = v.toString();
			vegetables.add(s);
		}
		model.addAttribute("vegetables", vegetables);
		
		Set<String> dough = new HashSet<String>();
		for (Dough d : Dough.values()) {
			String s = d.toString();
			dough.add(s);
		}
		model.addAttribute("dough", dough);
		
		Set<String> pizzaSauce = new HashSet<String>();
		for (PizzaSauce d : PizzaSauce.values()) {
			String s = d.toString();
			pizzaSauce.add(s);
		}
		model.addAttribute("pizzaSauce", pizzaSauce);
		
		Set<String> size = new HashSet<String>();
			for (Size d : Size.values()) {
				String s = d.toString();
				size.add(s);
		}
		model.addAttribute("size", size);
		
		
		
		CustomPizza pizza = new CustomPizza("img/custmPizza.png");
		model.addAttribute("newPizza", pizza);
		return "createPizza";
	}
	@RequestMapping(value="/create",method = RequestMethod.POST)
	public String AddSuplements(Model model, @ModelAttribute CustomPizza newPizza) {
		Set<Addable> suplements = newPizza.getSupplements();
		Set<String> stringSupplements;
		String dough = newPizza.getStringDough();
		String size = newPizza.getStringSize();
		String pizzaSauce = newPizza.getStringPizzaSauce();
		try {
			
			stringSupplements = newPizza.getStringSupplements();
			newPizza.setName("Your Pizza");
			newPizza.setSupplements(stringSupplements);
			newPizza.setPizzaSauce(pizzaSauce);
			newPizza.setSize(size);
			newPizza.setDough(dough);
			model.addAttribute("stringSupplements", stringSupplements);
			dao.addNewCustomPizza(newPizza);
			
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return "redirect:index.html";
		}
		
		
		model.addAttribute("suplements", suplements);
		model.addAttribute("newPizza", newPizza);
		return "pizzaAddedToOrder";
	}
	
	
	

	@RequestMapping(value="/added",method = RequestMethod.POST)
	public String addPizza(Model model,HttpSession s ,HttpServletRequest request) throws URLException {
		if (s==null || s.getAttribute("loggedUser")==null) {
			return "redirect:/index";
		}
		User user = (User) s.getAttribute("loggedUser");
		Order order = odao.getActiveOrderForUser(user);
		long id = Long.parseLong(request.getParameter("id"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		try {
			Product product = dao.getProductById(id);
			order.addProduct(product, quantity);
			model.addAttribute("order", order);
			model.addAttribute("user", user);
			
		} catch (ProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			return "create";
		}
		return "redirect:cart";
	}
}
