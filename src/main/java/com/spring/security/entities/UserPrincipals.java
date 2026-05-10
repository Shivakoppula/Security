package com.spring.security.entities;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipals implements UserDetails {
	
	
private Users user;
	
	public UserPrincipals(Users user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton( new SimpleGrantedAuthority("User"));
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}


	}


