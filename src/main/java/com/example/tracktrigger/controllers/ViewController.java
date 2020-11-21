package com.example.tracktrigger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	@RequestMapping("/")
	public String welcome() {
	        return "Login";
	 }
	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}
}


