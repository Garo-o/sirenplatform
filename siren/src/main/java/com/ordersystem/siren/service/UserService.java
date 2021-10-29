package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.Authority;
import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.dto.UserRequestDto;
import com.ordersystem.siren.jwt.JwtTokenProvider;
import com.ordersystem.siren.repository.UserRepository;
import com.ordersystem.siren.exception.UserDuplicateException;
import com.ordersystem.siren.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Transactional
    public User join(UserRequestDto.SignUp signup){
        checkDuplicateUserEmail(signup.getEmail());
        User user = User.builder()
                .email(signup.getEmail())
                .password(passwordEncoder.encode(signup.getPassword()))
                .activated(true)
                .name(signup.getName())
                .authorities(Collections.singleton(Authority.builder().authorityName(signup.getAuth()).build()))
                .build();
        return userRepository.save(user);
    }
    private void checkDuplicateUserEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) throw new UserDuplicateException(email +" already exist.");
    }

    public UserRequestDto.Token login(UserRequestDto.Login login){
        if(userRepository.findByEmail(login.getEmail()).isEmpty()){
            return null;
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        UserRequestDto.Token tokenDto = jwtTokenProvider.createToken(authentication);

        redisUtil.set(authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpireTime().getTime());
        return tokenDto;
    }

    public boolean logout(UserRequestDto.Logout logout){
        if(!jwtTokenProvider.validateToken(logout.getAccessToken())){
            return false;
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        if(redisUtil.get(authentication.getName())!=null){
            redisUtil.delete(authentication.getName());
        }

        long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisUtil.set(logout.getAccessToken(), "blackList", expiration);
        return true;
    }

    public UserRequestDto.Token reGenerateToken(Authentication auth, UserRequestDto.NewToken newToken){
        redisUtil.set(newToken.getAccessToken(), "blacklist", jwtTokenProvider.getExpiration(newToken.getAccessToken()));
        UserRequestDto.Token token = jwtTokenProvider.createToken(auth);
        redisUtil.set(auth.getName(), token.getRefreshToken(), token.getRefreshTokenExpireTime().getTime());
        return token;
    }
}
