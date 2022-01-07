package com.github.kvcheban.hellosecurity;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@GetMapping("/")
	public String home(Authentication auth) {
		return "home";
	}

	@GetMapping("/userPage")
	public String user() {
		return "userPage";
	}

	@GetMapping("/adminPage")
	public String admin() {
		return "adminPage";
	}
}
