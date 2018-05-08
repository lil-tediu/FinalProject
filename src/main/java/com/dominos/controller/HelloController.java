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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dominos.model.address.Address;
import com.dominos.model.address.IAddressDAO;
import com.dominos.model.user.User;
import com.dominos.model.user.UserDao;

@Controller
public class HelloController {
	@Autowired
	private UserDao dao;

	@Autowired
	private IAddressDAO ad;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String hello(Model model, HttpSession s) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			User user = new User();
			model.addAttribute("user", user);
			return "index";
		}

		// HttpSession session = request.getSession();

		// if (session.getAttribute("loggedUser")!=null) {
		// session.invalidate();
		// }

		return "indexLogged";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String helloLogin(Model model) {

		User user = new User();
		model.addAttribute("user", user);
		return "index1";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String register(@ModelAttribute @Valid User user, BindingResult result, HttpSession s) {
		try {

			if (result.hasFieldErrors()) {
				// If not a valid user then add error
				// else proceed to user welcome page

				result.addError(new ObjectError("err", "Invalid input"));
				return "index";

			} else {
				if (dao.existsUser(user.getEmail(), user.getPassword())) {
					result.addError(new ObjectError("err", "User exist"));
				//	System.out.println("In exust user");
					return "index";
				} else {
					dao.register(user);
					// HttpSession session = request.getSession();
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
	public String login(Model model, HttpSession s, @ModelAttribute @Valid User user, BindingResult result) {
		try {

			if (result.hasFieldErrors()) {
				result.addError(new ObjectError("err", "Invalid input"));
				return "index1";
			}
			if (dao.existsUser(user.getEmail(), user.getPassword())) {
				user = dao.getUser(user.getEmail());
				// HttpSession session = request.getSession();
				s.setMaxInactiveInterval(120);
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


	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	private String doUpdateProfile(@ModelAttribute User registeredUser,HttpSession s,HttpServletRequest request,final RedirectAttributes redirectAttributes) {
		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}


	//	System.out.println(((User) s.getAttribute("loggedUser")).getId());
		
		registeredUser.setId(((User) s.getAttribute("loggedUser")).getId());
		
		String fName=request.getParameter("firstname");
	//	System.out.println(fName);
		registeredUser.setFirstName(fName);
		
		String lName=request.getParameter("lastname");
//		System.out.println(lName);
		registeredUser.setLastName(lName);
		
		String mail=request.getParameter("email");
	//	System.out.println(mail);
		registeredUser.setEmail(mail);

		String pass1=request.getParameter("password");
		String pass2=request.getParameter("password2");
		
		if(!pass1.equals(pass2)) {

			return "updateProfile";

		}else {
		
		registeredUser.setPassword(pass1);
	//	System.out.println(pass1);
		try {
			dao.updateUser(registeredUser);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";

			
		}

		s.setAttribute("loggedUser", registeredUser);

		return "succesfullUpdate";
		
		}
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
	private String updateProfile(Model model, HttpSession s,HttpServletRequest request) {

		if (s == null || s.getAttribute("loggedUser") == null) {
			return "redirect:/index";
		}

		User u = new User();
//		User loggedUser = (User) s.getAttribute("loggedUser");
//		if (loggedUser != null) {
//			System.out.println("User exist");
//		} else {
//			System.out.println("User does not exist");
//		}
		model.addAttribute("updatedUser", u);
		request.setAttribute("equallPassword", null);

		return "updateProfile";
	}

}
