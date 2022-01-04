package com.github.kvcheban.hellosecurity;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MvcController {

	@GetMapping("/")
	@ResponseBody
	public String home(Authentication auth) {
		String userName = auth.getName();
		String authorities = getAuthoritiesString(auth);
		return "Welcome, <b>" + userName + "</b>. Your authorities: " + authorities + ".";
	}

	private String getAuthoritiesString(Authentication auth) {
		return auth.getAuthorities()
				.stream()
				.map(e -> e.getAuthority())
				.collect(Collectors.joining(", "));
	}

	@GetMapping("/userPage")
	@ResponseBody
	public String user() {
		return "User-only page";
	}

	@ResponseBody
	@GetMapping("/adminPage")
	public String admin() {
		return "Admin-only page";
	}
}
