package com.ordersystem.siren.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String GRANT_TYPE = "Bearer";
    private static final long accessTokenExpireTime = 1800000;       //30 min
    private static final long refreshTokenExpireTime = 604800000;    //7 days
    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] decode = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(decode);
    }

    public JwtToken createToken(Authentication auth){
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Date now = new Date();
        Date accessExpireTime = new Date(now.getTime()+accessTokenExpireTime);
        Date refreshExpireTime = new Date(now.getTime()+refreshTokenExpireTime);

        String accessToken = Jwts.builder()
                .setSubject(auth.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(accessExpireTime)
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(accessExpireTime)
                .compact();

        logger.info("Refresh token generated token valid to "+ accessExpireTime.toString());

        return JwtToken.builder()
                .grantType(GRANT_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshExpireTime)
                .build();
    }

    public Authentication getAuthentication(String token){
        Claims claims = getClaimFromToken(token);

        List<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails user = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(user,"",authorities);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e){
            logger.info("Invalid JWT signature.", e);
        }catch (ExpiredJwtException e){
            logger.info("Expired ACCESS_JWT", e);
        }catch (UnsupportedJwtException e){
            logger.info("Unsupported JWT", e);
        }catch (IllegalArgumentException e){
            logger.info("JWT claims string is empty", e);
        }
        return false;
    }

    private Claims getClaimFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
    }

    public long getExpiration(String token){
        Date expiration = getClaimFromToken(token).getExpiration();
        return expiration.getTime() - new Date().getTime();
    }

    public String resolveToken(HttpServletRequest request){
        String header = request.getHeader(JwtFilter.AUTHORIZATION_HEAD);
        if(StringUtils.hasText(header) && header.startsWith(GRANT_TYPE)){
            return header.substring(7);
        }
        return null;
    }
}
