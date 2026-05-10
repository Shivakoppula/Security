package com.spring.security.service;

import org.springframework.stereotype.Service;

@Service
public class JWTService {

	public String generateToken() {
		
		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InNoaXZha29wcHVsYSIsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNTE2MjQ5MDIyfQ.MTqgzlQctCzTlzOZ-3PTigGKvw8b_qUe-Upoa-TvdG8";
	}

}
