package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.dto.UserDto;
import com.ordersystem.siren.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/v1/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.join(userDto));
    }

}
