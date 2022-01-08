package com.github.kvcheban.hellosecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${hellosecurity.useOauth:true}")
	private boolean useOauth;

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
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/login*").permitAll()
				.antMatchers("/admin-page").hasRole("ADMIN")
				.antMatchers("/user-page").hasRole("USER")
				.anyRequest().authenticated();

		configureLogin(http);
	}

	private void configureLogin(HttpSecurity http) throws Exception {
		if (useOauth) {
			http.oauth2Login();
		} else {
			http.formLogin()
				.loginPage("/login")
				.failureUrl("/login-error");
		}
	}
}
