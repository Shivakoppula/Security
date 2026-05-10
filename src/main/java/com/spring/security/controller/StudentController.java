package com.spring.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.entities.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController

public class StudentController {
	private List<Student>stds=new ArrayList<>(Arrays.asList(new Student(1,"shiva",60),
			new Student(2,"narashmiha",70)));
	
	@GetMapping("/std")
	public List<Student>get(){
		return stds;
	}
	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	@PostMapping("/std")
	public Student add(@RequestBody Student std) {
		stds.add(std);
		return std;
	}
}
