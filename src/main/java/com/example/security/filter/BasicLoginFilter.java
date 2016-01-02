package com.example.security.filter;

import com.example.domain.User;
import com.example.domain.security.UserAuthentication;
import com.example.security.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasicLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final TokenAuthenticationService tokenAuthenticationService;

	public BasicLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.tokenAuthenticationService = tokenAuthenticationService;
		super.setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

//        User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        String userName = request.getHeader("X-Auth-Username");
        String password = request.getHeader("X-Auth-Password");
		UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(userName, password);
		return super.getAuthenticationManager().authenticate(loginToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											FilterChain chain, Authentication authentication) throws IOException, ServletException {

		// Lookup the complete User object from the database and create an Authentication for it
		User authenticatedUser = (User) authentication.getPrincipal();
		UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		String token = this.tokenAuthenticationService.addTokenToHeader(response, userAuthentication);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(token);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);

	}

}