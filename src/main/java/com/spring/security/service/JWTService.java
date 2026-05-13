package com.spring.security.service;


import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
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
		// building tokens
		Map<String, Object>claims=new HashMap<String, Object>();
		return Jwts.builder() // building token by using claims
				.claims()
				.add(claims)
				.subject(username) //verify username
				.issuedAt(new Date(System.currentTimeMillis())) // checking date of token generated
				.expiration(new Date(System.currentTimeMillis()+ 60*60*30)) // verify token by date expiration
				.and()
				.signWith(getKey()) // singing with key
				.compact();
		//return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InNoaXZha29wcHVsYSIsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNTE2MjQ5MDIyfQ.MTqgzlQctCzTlzOZ-3PTigGKvw8b_qUe-Upoa-TvdG8";
	}

	//token convet string to bytes
	private SecretKey getKey() {
		byte[] keyByte=Decoders.BASE64.decode(secrete);
		return Keys.hmacShaKeyFor(keyByte);
		 
	}

	//extract the username by using extractclaims methods by jwt token
	public String extractUserName(String token) {
		// extract the username from claims
		return extractclaim(token,Claims::getSubject);
	}
 
	// extract the claims 
	private <T> T extractclaim(String token, Function<Claims, T>claimResolver) {
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(getKey())
				.build().parseSignedClaims(token)
				.getPayload();
	}

	//validating the  token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName=extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}

	//valiadting token expire by changing the date verifying
	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}

	//logic for expiration
	private Date extractExpiration(String token) {
		
		return extractclaim(token, Claims::getExpiration);
	}

}
