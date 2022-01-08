package com.github.kvcheban.hellosecurity;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController {

	private static final String COOKIE_USERNAME = "userName";
	private static final String COOKIE_USEREMAIL = "userEmail";

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
	public String home(Principal principal, Model model) {
		if (principal instanceof OAuth2AuthenticationToken) { // if Oauth2 is enabled
			model.addAttribute("userInfo", 
					((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes());
		}
		return "home";
	}

	@GetMapping("/user-page")
	public String userPage() {
		return "userPage";
	}

	@GetMapping("/admin-page")
	public String adminPage() {
		return "adminPage";
	}

	@GetMapping("/cookies")
	public String getCookies(HttpServletRequest request, Model model) {
		model.addAttribute("cookies", request.getCookies());
		return "cookies";
	}

	@GetMapping("/contacts")
	public String getContacts(
			@CookieValue(name = COOKIE_USERNAME, defaultValue = "") String name,
			@CookieValue(name = COOKIE_USEREMAIL, defaultValue = "") String email,
			Model model) {
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		return "contacts";
	}

	@PostMapping("/contacts")
	public String setContacts(@ModelAttribute("name") String name,
			@ModelAttribute("email") String email,
			HttpServletResponse response) {
		response.addCookie(createNameCookie(name));
		response.addCookie(createEmailCookie(email));
		return "redirect:/";
	}

	private Cookie createNameCookie(String name) {
		Cookie cookie = new Cookie(COOKIE_USERNAME, name);
		cookie.setMaxAge(60);
		cookie.setPath("/");
		return cookie;
	}

	private Cookie createEmailCookie(String email) {
		Cookie cookie = new Cookie(COOKIE_USEREMAIL, email);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		return cookie;
	}

	@GetMapping("/userinfo")
	public String getUserInfo(OAuth2AuthenticationToken token, Model model) {
		OAuth2User oauthUser = token.getPrincipal();
		model.addAttribute("userInfo", oauthUser.getAttributes());
		return "userInfo";
	}
}
