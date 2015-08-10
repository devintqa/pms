package com.psk.pms.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class AuthenticationHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String userId = request.getParameter("employeeId");
		if (userId != null) {
			response.sendRedirect(request.getContextPath() + "/emp/myview/"
					+ userId);
		} else {
			SavedRequest savedRequest = new HttpSessionRequestCache()
					.getRequest(request, response);
			if (savedRequest != null) {
				response.sendRedirect(savedRequest.getRedirectUrl());
			} else {
				response.sendRedirect(request.getContextPath() + "/");
			}
		}
	}

}
