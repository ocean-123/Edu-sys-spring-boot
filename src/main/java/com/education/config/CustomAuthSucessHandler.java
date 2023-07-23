package com.education.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSucessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/profile");
		} else if (roles.contains("ROLE_USER")) {
			response.sendRedirect("/user/profile");
		} else {
			// Handle other roles or unauthorized cases
			response.sendRedirect("/error"); // Customize the error page URL
		}

	}

}
