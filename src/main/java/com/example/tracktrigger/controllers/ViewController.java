package com.example.tracktrigger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	@RequestMapping("/")
	public String home() {
	        return "index";
	 }
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}
	@RequestMapping("/userlogin")
	public String login() {
		return "login";
	}
	@RequestMapping("/verify")
	public String verify() {
		return "verification";
	}
	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}
}


