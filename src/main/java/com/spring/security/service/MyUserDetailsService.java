package com.spring.security.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.entities.UserPrincipals;
import com.spring.security.entities.Users;
import com.spring.security.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user= repo.findByUsername(username);
		if(user==null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipals(user);
	}

}
