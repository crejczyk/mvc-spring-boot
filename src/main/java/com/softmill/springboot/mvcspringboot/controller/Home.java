package com.softmill.springboot.mvcspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

	@GetMapping()
	public String index() {
		return "index";
	}
	
}