package com.github.kvcheban.hellosecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user1").password("{noop}user1Pass").roles("USER")
			.and()
			.withUser("user2").password("{noop}user2Pass").roles("USER")
			.and()
			.withUser("admin").password("{noop}adminPass").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/adminPage").hasRole("ADMIN")
			.antMatchers("/userPage").hasRole("USER")
			.anyRequest().authenticated()
			.and()
			.formLogin();
	}
}
