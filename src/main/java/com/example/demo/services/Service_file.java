package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.principles.UserPrincilpals;
import com.example.demo.repositorys.User_repo;

@Service
public class Service_file implements UserDetailsService {
	@Autowired
	private User_repo sr;

	public void registration(User s) {
		sr.save(s);
	}

	public User signup(String email, String Password) {
		User us = sr.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));
		if (!us.getPassword().equals(Password)) {
			throw new RuntimeException("Password not found");
		}
		return us;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User us = sr.findByEmail(username).orElseThrow(() -> new RuntimeException("Email not found"));
		
		return new UserPrincilpals(us);
	}
}
