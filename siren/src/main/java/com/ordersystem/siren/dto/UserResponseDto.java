package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.Authority;
import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.stream.Collectors;

public class UserResponseDto {
    @ToString
    public static class UserDto{
        private String email;
        private String name;
        private String auth;

        public UserDto(User user){
            this.email = user.getEmail();
            this.name = user.getName();
            this.auth = user.getAuthorities().stream().map(Authority::getAuthorityName).collect(Collectors.joining(","));
        }
    }
    @Getter
    @ToString
    public static class TokenDto {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Date refreshTokenExpireTime;

        public TokenDto(JwtToken token){
            this.accessToken=token.getAccessToken();
            this.refreshToken=token.getRefreshToken();
            this.grantType=token.getGrantType();
            this.refreshTokenExpireTime=token.getRefreshTokenExpireTime();
        }
    }
}
