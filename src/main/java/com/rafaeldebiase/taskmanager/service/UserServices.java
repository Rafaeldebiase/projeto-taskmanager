package com.rafaeldebiase.taskmanager.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.rafaeldebiase.taskmanager.security.UserSS;

public class UserServices {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
	}

	
}
