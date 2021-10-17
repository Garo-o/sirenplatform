package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.Authority;
import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.util.UserRole;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;

    private String auth = UserRole.ROLE_USER;
}
