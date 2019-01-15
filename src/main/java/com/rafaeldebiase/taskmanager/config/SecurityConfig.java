/**
 * 
 */
package com.rafaeldebiase.taskmanager.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rafaeldebiase.taskmanager.security.JWTAutenticationFilter;
import com.rafaeldebiase.taskmanager.security.JWTAuthorizationFilter;
import com.rafaeldebiase.taskmanager.security.JwtUtil;
import com.rafaeldebiase.taskmanager.service.UserDetailService;


/**
 * @author Rafael de Biase
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailService userDetailService;  
	
	@Autowired
	private JwtUtil jwtUtil;
		
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/tarefas/**",
			"/usuarios/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/usuarios/**"
	};
	
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests()
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
		.anyRequest().authenticated();
		http.addFilter(new JWTAutenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	 public void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
	 }
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
