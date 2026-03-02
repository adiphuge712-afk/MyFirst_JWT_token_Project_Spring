package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtutil;
import com.example.demo.DTO.Login_Dto;
import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.services.Service_file;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class Mycontroller {
	@Autowired
	Service_file ss;
	@Autowired
	jwtutil jwtUtil;
	@Autowired
	private AuthenticationManager authmaneger;
	@GetMapping("/")
	public ResponseEntity<?> test(HttpServletRequest req) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tested ok :"+ req.getSession().getId());
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
	public ResponseEntity<String> loging(@RequestBody Login_Dto user) {

//		try {
////			System.out.println(user.getEmail());
//			Authentication authentication=authmaneger.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
////			User use=ss.signup(user.getEmail(),user.getPassword());
//			if(authentication.isAuthenticated()) {
//				User use=ss.signup(user.getEmail(),user.getPassword());
//				String token = jwtUtil.generateToken(use);
//				return ResponseEntity.ok(token);
//			}else {
//				return ResponseEntity.ok("FAil to authenticate");
//			}
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Fail");
//		}
		if(user.getRole().equals("USER")) {
			try {
//				System.out.println(user.getEmail());
				Authentication authentication=authmaneger.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
//				User use=ss.signup(user.getEmail(),user.getPassword());
				if(authentication.isAuthenticated()) {
					User use=ss.signup(user.getEmail(),user.getPassword());
					String token = jwtUtil.generateToken(use);
					return ResponseEntity.ok(token);
				}else {
					return ResponseEntity.ok("FAil to authenticate");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Fail");
			}
		} else if(user.getRole().equals("ADMIN")) {
			try {
//				System.out.println(user.getEmail());
				Authentication authentication=authmaneger.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
//				User use=ss.signup(user.getEmail(),user.getPassword());
				if(authentication.isAuthenticated()) {
					Admin use=ss.adminsignup(user.getEmail(),user.getPassword());
					String token = jwtUtil.generateToken(use);
					return ResponseEntity.ok(token);
				}else {
					return ResponseEntity.ok("FAil to authenticate");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Fail");
			}
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Fail No Data match");
		}
	}
	@GetMapping("/viewDetails")
	public ResponseEntity<List<User>> viewDetails() {
		try {
		List<User> us=	ss.viewDetails();
			
			return ResponseEntity.ok(us);
			
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
	}
	@GetMapping("/viewDetailsadmin")
	public ResponseEntity<List<Admin>> viewDetailsadmin() {
		try {
		List<Admin> us=	ss.viewDetailsadmin();
			
			return ResponseEntity.ok(us);
			
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
	}
	
}
