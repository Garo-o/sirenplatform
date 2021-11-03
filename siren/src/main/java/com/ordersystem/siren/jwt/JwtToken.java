package com.ordersystem.siren.jwt;

import com.ordersystem.siren.dto.UserRequestDto;
import com.ordersystem.siren.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpireTime;
}
