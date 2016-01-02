package com.example.security;

import com.example.domain.security.UserAuthentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

	private final TokenCacheService tokenCacheService;

	public TokenAuthenticationService() {
        this.tokenCacheService = new TokenCacheService();
	}

	public String addTokenToHeader(HttpServletResponse response, UserAuthentication userAuthentication) {
		String token = this.tokenCacheService.generateToken();
        this.tokenCacheService.store(token, userAuthentication);
		response.addHeader(AUTH_HEADER_NAME, token);
        return token;
	}

	public UserAuthentication checkTokenFromHeader(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			UserAuthentication userAuthentication = this.tokenCacheService.retrieve(token);
			if (userAuthentication != null) {
				return userAuthentication;
			}
		}
		return null;
	}

}
