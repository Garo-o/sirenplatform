package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.Authority;
import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.repository.UserRepository;
import com.ordersystem.siren.dto.UserDto;
import com.ordersystem.siren.exception.UserDuplicateException;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public User join(UserDto userDto){
        checkDuplicateUserEmail(userDto.getEmail());
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .activated(true)
                .name(userDto.getName())
                .authorities(Collections.singleton(Authority.builder().authorityName(userDto.getAuth()).build()))
                .build();
        return userRepository.save(user);
    }

    private void checkDuplicateUserEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) throw new UserDuplicateException(email +" already exist.");
    }
}
