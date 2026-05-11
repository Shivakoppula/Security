package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.entities.Users;
import com.spring.security.service.UserService;

@RestController
@RequestMapping()
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public Users  registration(@RequestBody Users users) {
		 return service.register(users);
		//return users;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		
	//System.out.println(user);
		return service.verify(user);
	}
	

}
