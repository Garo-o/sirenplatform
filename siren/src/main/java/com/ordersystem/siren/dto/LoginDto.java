package com.ordersystem.siren.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LoginDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
