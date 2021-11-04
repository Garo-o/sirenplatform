package com.ordersystem.siren.dto;

import lombok.*;

public class UserRequestDto {
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
        private String auth;
    }
}
