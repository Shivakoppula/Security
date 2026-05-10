package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Hello {
	@GetMapping("/")
	public String greet(HttpServletRequest req) {
		return "welcome back shiva "+ req.getSession().getId();
	}

}
