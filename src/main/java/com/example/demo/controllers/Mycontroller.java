package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtutil;
import com.example.demo.entity.User;
import com.example.demo.services.Service_file;

@RestController
public class Mycontroller {
	@Autowired
	Service_file ss;
	@Autowired
	jwtutil jwtUtil;
	
	@GetMapping("/")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tested ok");
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		try {
			ss.registration(user);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registration complete");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Fail");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> loging(@RequestBody User user) {
		try {
			ss.signup(user.getEmail(),user.getPassword());
			String token = jwtUtil.generateToken(user.getEmail());
			System.out.println("Token is: "+token);
			return ResponseEntity.ok(token);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Fail");
		}
	}
	
}
