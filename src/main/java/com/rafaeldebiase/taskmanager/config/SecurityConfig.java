/**
 * 
 */
package com.rafaeldebiase.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rafaeldebiase.taskmanager.security.JWTAutenticationFilter;
import com.rafaeldebiase.taskmanager.security.JwtUtil;
import com.rafaeldebiase.taskmanager.service.UserDetailService;


/**
 * @author Rafael de Biase
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailService userDetailService;  
	
	@Autowired
	private JwtUtil jwtUtil;
		
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
			"/usuarios/**",
			"/tarefas/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/tarefas/**",
			"/usuarios/**"
	};
	
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests()
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
		.anyRequest().authenticated();
		http.addFilter(new JWTAutenticationFilter(authenticationManager(), jwtUtil));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	 public void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
	 }
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
