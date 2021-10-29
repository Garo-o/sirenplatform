package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.dto.*;
import com.ordersystem.siren.jwt.JwtTokenProvider;
import com.ordersystem.siren.repository.UserRepository;
import com.ordersystem.siren.service.UserService;
import com.ordersystem.siren.util.RedisUtil;
import com.ordersystem.siren.util.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class SignController {
    private final Response response;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRequestDto.SignUp signUp){
        return response.success(userService.join(signUp),"Sign up success.",HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody UserRequestDto.Login login){
        UserRequestDto.Token token = userService.login(login);
        if(token == null){
            return response.fail("Invalid Email.", HttpStatus.BAD_REQUEST);
        }
        return response.success(token, "sign in success.", HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signout(@Valid @RequestBody UserRequestDto.Logout logout){
        return userService.logout(logout) ?
                response.success("sign out success.") :
                response.fail("Invalid access token.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody UserRequestDto.NewToken newToken){
        if(!jwtTokenProvider.validateToken(newToken.getRefreshToken())){
            return response.fail("Invalid refresh token.", HttpStatus.BAD_REQUEST);
        }

        Authentication auth = jwtTokenProvider.getAuthentication(newToken.getAccessToken());
        String refreshToken = (String)redisUtil.get(auth.getName());
        if(!refreshToken.equals(newToken.getRefreshToken())){
            return response.fail("Wrong request.", HttpStatus.BAD_REQUEST);
        }

        return response.success(userService.reGenerateToken(auth, newToken), "Regenerated token.", HttpStatus.OK);
    }
}
