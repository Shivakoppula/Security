package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.entities.Users;
import com.spring.security.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	//becrypt need to create object for BCryptPasswordEncoder pass value for strength
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	public Users register(Users users) {
		
		
		//for becrypting password
		users.setPassword(encoder.encode(users.getPassword()));
	  return repo.save(users);
	
	}

}
