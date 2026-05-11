package com.spring.security.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	private String secrete="";
	//secrete jey generation
	public JWTService() {
		try {
			KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk= keyGen.generateKey();
			secrete =Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	//token generation

	public String generateToken(String username) {
		
		Map<String, Object>claims=new HashMap<String, Object>();
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()*60*60*30))
				.and()
				.signWith(getKey())
				.compact();
		//return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InNoaXZha29wcHVsYSIsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNTE2MjQ5MDIyfQ.MTqgzlQctCzTlzOZ-3PTigGKvw8b_qUe-Upoa-TvdG8";
	}

	private Key getKey() {
		byte[] keyByte=Decoders.BASE64.decode(secrete);
		return Keys.hmacShaKeyFor(keyByte);
		 
	}

}
