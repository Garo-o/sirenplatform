package com.ordersystem.siren.jwt;

import com.ordersystem.siren.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
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
    private static final String GRANT_TYPE = "Bearer";

    private final JwtTokenProvider provider;
    private final RedisUtil redisUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = resolveToken(request);

        if(StringUtils.hasText(jwt) && provider.validateToken(jwt)) {
            String isNull = (String) redisUtil.get(jwt);
            if (ObjectUtils.isEmpty(isNull)) {
                Authentication authentication = provider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request){
        String header = request.getHeader(AUTHORIZATION_HEAD);
        if(StringUtils.hasText(header) && header.startsWith(GRANT_TYPE)){
            return header.substring(7);
        }
        return null;
    }
}
