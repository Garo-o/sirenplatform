package com.ordersystem.siren.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TokenDto {
    private String token;
}
