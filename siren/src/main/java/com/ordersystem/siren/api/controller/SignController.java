package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.dto.LoginDto;
import com.ordersystem.siren.dto.TokenDto;
import com.ordersystem.siren.jwt.JwtFilter;
import com.ordersystem.siren.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SignController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/v1/signin")
    public ResponseEntity<TokenDto> signin(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),loginDto.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtTokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEAD,"Bearer "+jwt);
        return new ResponseEntity<>(TokenDto.builder().token(jwt).build(),httpHeaders, HttpStatus.OK);
    }
}
