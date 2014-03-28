package com.cric.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cric.util.CricUtil;
import com.cric.util.ValidateUtil;

@Controller
public class AppController {

	@RequestMapping("/login.htm")
	public String login() {
		return "login";
	}
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute("isAdmin");
		session.removeAttribute("uname");
		session.invalidate();
		return "redirect:login.htm";
	}
	
	@RequestMapping(value = "/validate.htm", method = RequestMethod.GET)
	public String validate1(){
		return "redirect:login.htm";
	}

	@RequestMapping(value = "/validate.htm", method = RequestMethod.POST)
	public String validate(
			@RequestParam(value = "username", required = true) String name,
			@RequestParam(value = "password", required = true) String password,
			Model model,
			HttpSession session) {
		System.out.println("name " + name);
		
		if (!ValidateUtil.isValid(name, password)) {
			session.setAttribute("message", "Username or Password is invalid.");
			return "redirect:login.htm";
		}
		
		if( ValidateUtil.isAdmin(name) ){
			session.setAttribute("isAdmin", true);
		}
		
		session.setAttribute("uname", name);
		
		return "redirect:home.htm";
	}
	
	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public String home(){

		return "home";
	}


	@RequestMapping(value = "/init.htm", headers = "Accept=application/json")
	public @ResponseBody String init() {
		return CricUtil.getModelJSON();
	}

}