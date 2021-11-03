package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.dto.UserRequestDto;
import com.ordersystem.siren.dto.UserResponseDto;
import com.ordersystem.siren.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@SpringBootTest
@Transactional
class SignControllerTest {
    @Autowired private SignController signController;
    @Autowired private UserService userService;

    @BeforeEach
    public void setUp(){
    }

    @Test
    public void login() throws Exception{
        //given
        UserRequestDto.Login  login = UserRequestDto.Login.builder().email("admin").password("admin").build();
        Errors errors = Mockito.mock(Errors.class, "errors");
        //when
        ResponseEntity<?> signin = signController.signin(login,errors);
        //then
        System.out.println("");
        System.out.println("signin = " + signin.getBody().toString());
    }

    @Test
    public void failedLogin() throws Exception{
        //given
        UserRequestDto.Login  login = UserRequestDto.Login.builder().email("admin").password("admin@@").build();
        Errors errors = Mockito.mock(Errors.class, "errors");
        //when
        Assertions.assertThrows(Exception.class,()->{
           signController.signin(login,errors);
        });
        //then
    }

    @Test
    public void createMember() throws Exception{
        //given
        UserRequestDto.SignUp signUp1 = UserRequestDto.SignUp.builder().email("hong").name("hong").password("hong").build();
        UserRequestDto.SignUp signUp2 = UserRequestDto.SignUp.builder().email("park").name("park").password("park").build();
        Errors errors = Mockito.mock(Errors.class, "errors");
        //when
        ResponseEntity<?> signup11 = signController.signup(signUp1,errors);
        ResponseEntity<?> signup22 = signController.signup(signUp2,errors);
        //then
        System.out.println("");
        System.out.println("signup11 = " + signup11);
        System.out.println("signup22 = " + signup22);
    }

    @Test
    public void failedCreateMember() throws Exception{
        //given
        UserRequestDto.SignUp signUp1 = UserRequestDto.SignUp.builder().email("hong").name("hong").password("hong").build();
        UserRequestDto.SignUp signUp2 = UserRequestDto.SignUp.builder().email("hong").name("hong").password("hong").build();
        Errors errors = Mockito.mock(Errors.class, "errors");
        ResponseEntity<?> signup11 = signController.signup(signUp1,errors);
        System.out.println("signup1 = " + signup11.getBody().toString());

        //when
        Assertions.assertThrows(Exception.class,()->{
            signController.signup(signUp2,errors);
        });

        //then
    }
    @Test
    public void changeRefresh() throws Exception{
        //given
        UserRequestDto.Login  login = UserRequestDto.Login.builder().email("admin").password("admin").build();
        UserResponseDto.TokenDto token = new UserResponseDto.TokenDto(userService.login(login));
        UserRequestDto.NewToken newToken = UserRequestDto.NewToken.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken()).build();
        Errors errors = Mockito.mock(Errors.class, "errors");
        //when
        ResponseEntity<?> refresh = signController.refresh(newToken,errors);
        //then
        System.out.println("");
        System.out.println("first token = " + token.toString());
        System.out.println("refresh token= " + refresh.getBody().toString());
    }

}