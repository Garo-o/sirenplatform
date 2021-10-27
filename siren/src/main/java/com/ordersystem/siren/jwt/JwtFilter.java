package com.ordersystem.siren.jwt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEAD="Authorization";
    private final JwtTokenProvider provider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = resolveToken(request);
        String requestUTI = request.getRequestURI();

        if(StringUtils.hasText(jwt) && provider.validateToken(jwt)){
            Authentication authentication = provider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context '{}' saved. uri: {}", authentication.getName(),requestUTI);
        }
        else{
            logger.debug("Invalid JWT token.");
        }
        chain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request){
        String header = request.getHeader(AUTHORIZATION_HEAD);
        if(StringUtils.hasText(header)){
            return header.substring(7);
        }
        return null;
    }
}
