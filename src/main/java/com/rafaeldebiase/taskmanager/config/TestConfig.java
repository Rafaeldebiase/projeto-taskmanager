package com.rafaeldebiase.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rafaeldebiase.taskmanager.service.DBservice;

/**
 * @author casa
 *
 */

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBservice dbservice;
	
	@Bean
	public Boolean instantiateDatabase() {
		dbservice.instantiateTestDatabase();
		return true;
	}
}