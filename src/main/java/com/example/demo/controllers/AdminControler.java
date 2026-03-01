package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtutil;
import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.services.Service_file;

@RestController
@RequestMapping("/admin")
public class AdminControler {
	@Autowired
	Service_file ss;
	@Autowired
	jwtutil jwtUtil;
	@Autowired
	private AuthenticationManager authmaneger;
	@GetMapping("/")
	public ResponseEntity<?> test(){
		try {
			return ResponseEntity.ok("Tested ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.ok("Fail");
		}
	}
	@PostMapping("/login")
	public ResponseEntity<String> loging(@RequestBody Admin user) {
		try {
			System.out.println(user.getEmail());
			Authentication authentication=authmaneger.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
//			User use=ss.signup(user.getEmail(),user.getPassword());
			if(authentication.isAuthenticated()) {
				Admin use=ss.adminsignup(user.getEmail(),user.getPassword());
				String token = jwtUtil.generateToken(use);
				return ResponseEntity.ok(token);
			}else {
				return ResponseEntity.ok("FAil to authenticate");
			}
		}catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Fail");
		}
	}
	@GetMapping("/viewDetails")
	public ResponseEntity<List<Admin>> viewDetails() {
		try {
		List<Admin> us=	ss.viewDetailsadmin();
			
			return ResponseEntity.ok(us);
			
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
	}
	
}
