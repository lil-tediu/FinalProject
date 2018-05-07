package com.dominos.controller;

import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dominos.model.address.Address;
import com.dominos.model.address.AddressDao;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Controller
public class HelloController {
	@Autowired
	private UserDao dao;

	@Autowired
	private AddressDao ad;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String hello(Model model, HttpSession s) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			User user = new User();
			model.addAttribute("user", user);
			return "index";
		}
		
//		HttpSession session = request.getSession();
		
//		if (session.getAttribute("loggedUser")!=null) {
//			session.invalidate();
//		}
		
		return "indexLogged";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String helloLogin(Model model) {
		
		User user = new User();
		model.addAttribute("user", user);
		return "index1";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String register( @ModelAttribute @Valid User user, BindingResult result,HttpSession s) {
		try {

			if (result.hasFieldErrors()) {
				// If not a valid user then add error
				// else proceed to user welcome page

				result.addError(new ObjectError("err", "Invalid input"));
				return "index";

			} else {
				if (dao.existsUser(user.getEmail(), user.getPassword())) {
					result.addError(new ObjectError("err", "User exist"));
					System.out.println("In exust user");
					return "index";
				} else {
					dao.register(user);
					//HttpSession session = request.getSession();
					s.setMaxInactiveInterval(120);
					s.setAttribute("loggedUser", user);
					return "redirect:/drinks";

				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		result.addError(new ObjectError("err", "Invalid input"));

		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpSession s, @ModelAttribute @Valid User user,
			BindingResult result) {
		try {

			if (result.hasFieldErrors()) {
				result.addError(new ObjectError("err", "Invalid input"));
				return "index1";
			}
			if (dao.existsUser(user.getEmail(), user.getPassword())) {
				user = dao.getUser(user.getEmail());
				//HttpSession session = request.getSession();
				s.setMaxInactiveInterval(12000);
				s.setAttribute("loggedUser", user);
				return "redirect:drinks";
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		result.addError(new ObjectError("err", "Invalid input"));
		return "index1";
	}
	
	// @RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
		//
		// private String updateProfile(User user, HttpSession sess){
		//
		// sess.setAttribute("user", user);
		//
		// return "updateProfile";
		// }
		// @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
		//
		// private String doUpdateProfile(User user, HttpSession sess){
		//
		// user.setId(((User) sess.getAttribute("user")).getId());
		// try {
		// dao.updateUser(user);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// try {
		// user = dao.getUser(user.getEmail());
		// } catch (ClassNotFoundException | SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return "redirect:index.html";
		//
		// }
		// sess.setAttribute("user", user);
		//
		// return "redirect:pizzas:";
		// }

		@RequestMapping(value = "/updateProfile")
		private String doUpdateProfile(Model model,User user,HttpSession sess) {
			System.out.println(((User) sess.getAttribute("loggedUser")).getId());
			user.setId(((User) sess.getAttribute("loggedUser")).getId());
			try {
				dao.updateUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user = dao.getUser(user.getEmail());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "redirect:index.html";

			}
			model.addAttribute("user",user);
			sess.setAttribute("loggedUser", user);

			return "updateProfile";
		}
	
}
