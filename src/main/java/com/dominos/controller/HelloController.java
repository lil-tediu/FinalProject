package com.dominos.controller;

import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dominos.model.address.Address;
import com.dominos.model.address.IAddressDAO;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Controller
public class HelloController {
	@Autowired
	private UserDao dao;


	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String hello(Model model, HttpSession s) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			User user = new User();
			model.addAttribute("user", user);
			return "index";
		}

		return "indexLogged";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String helloLogin(Model model) {

		User user = new User();
		model.addAttribute("user", user);
		return "index1";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String register(@ModelAttribute @Valid User user, BindingResult result, HttpSession s, ModelMap map) {
		try {

			if (result.hasFieldErrors()) {
				result.addError(new ObjectError("err", "Invalid input"));
				return "index";

			} else {
				if (dao.hasSuchEmail(user.getEmail())) {
					String message = "Sorry, this username already exist";
					map.put("error", message);
					return "index";
				} else {
					dao.register(user);
					s.setMaxInactiveInterval(120);
					s.setAttribute("loggedUser", user);
					return "redirect:/drinks";

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpSession s, @ModelAttribute @Valid User user, BindingResult result,
			ModelMap map) {
		try {

			if (result.hasFieldErrors()) {
				result.addError(new ObjectError("err", "Invalid input"));
				return "index1";
			}

			System.out.println(user.getPassword());
			if (dao.existsUser(user.getEmail(), user.getPassword())) {

				user = dao.getUser(user.getEmail());
				s.setMaxInactiveInterval(120);
				s.setAttribute("loggedUser", user);
				return "redirect:drinks";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		String errormsg = "Invalid username or password";
		map.put("errMsg1", errormsg);
		return "index1";
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	private String doUpdateProfile(@ModelAttribute User registeredUser, HttpSession s, HttpServletRequest request,
			ModelMap map) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}
		User loggedUser = (User) s.getAttribute("loggedUser");
		registeredUser.setId(loggedUser.getId());
		String fName = request.getParameter("firstname");
		registeredUser.setFirstName(fName);
		String lName = request.getParameter("lastname");
		registeredUser.setLastName(lName);
		registeredUser.setEmail(loggedUser.getEmail());

		String pass1 = request.getParameter("password");
		String pass2 = request.getParameter("password2");

		if (!pass1.equals(pass2)) {
			String errormsg = "Password does not match";
			map.put("error", errormsg);
			return "updateProfile";
		} else {
			registeredUser.setPassword(pass1);
			try {
				dao.updateUser(registeredUser);
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
			s.setAttribute("loggedUser", registeredUser);
			return "succesfullUpdate";

		}
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
	private String updateProfile(Model model, HttpSession s, HttpServletRequest request) {

		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}
		User u = new User();
		User loggedUser = (User) s.getAttribute("loggedUser");
		model.addAttribute("user", loggedUser);
		model.addAttribute("updatedUser", u);
		return "updateProfile";
	}

}
