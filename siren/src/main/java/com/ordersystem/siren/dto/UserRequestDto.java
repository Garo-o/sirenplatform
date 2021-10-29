package com.ordersystem.siren.dto;

import com.ordersystem.siren.util.UserRole;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserRequestDto {

    @Getter
    @Builder
    public static class Token {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Date refreshTokenExpireTime;
    }

    @Getter
    @Builder
    public static class NewToken {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class Logout {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    public static class SignUp{
        private String email;
        private String password;
        private String name;

        private String auth = UserRole.ROLE_USER;
    }
}
