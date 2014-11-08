package ca.liothe.bib.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.liothe.bib.model.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class Authentication {
	
	//private static final Logger logger = LoggerFactory.getLogger(Authentication.class);

	private static final String USERNAME = "stevendluke@gmail.com";
	private static final String PASSWORD = "steluk378";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid User user, BindingResult result, HttpSession session, Model model) {
		if(!result.hasErrors() && USERNAME.equals(user.getEmail()) && PASSWORD.equals(user.getPassword())){
			session.setAttribute("validated", true);
			return "redirect:/";
		}
		else{
			model.addAttribute("loginError", "Invalid Username/Password");
			return "login";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("validated", false);
		return "redirect:/";
	}
	
}
