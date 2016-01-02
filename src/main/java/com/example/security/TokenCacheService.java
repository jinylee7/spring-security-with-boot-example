package com.example.security;

import com.example.domain.security.UserAuthentication;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.token.TokenService;

import java.util.UUID;

public class TokenCacheService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final Cache restApiAuthTokenCache = CacheManager.getInstance().getCache("restApiAuthTokenCache");
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.debug("Evicting expired tokens");
        restApiAuthTokenCache.evictExpiredElements();
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void store(String token, UserAuthentication userAuthentication) {
        restApiAuthTokenCache.put(new Element(token, userAuthentication));
    }

    public boolean contains(String token) {
        return restApiAuthTokenCache.get(token) != null;
    }

    public UserAuthentication retrieve(String token) {
        Element element = restApiAuthTokenCache.get(token);
        if ( element == null )
            return null;
        else
            return (UserAuthentication) element.getObjectValue();
    }

}
