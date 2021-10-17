package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailService")
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user->toUserDetails(email, user)).orElseThrow(()->new UsernameNotFoundException(email+"is not found."));
    }

    public User toUserDetails(String email, com.ordersystem.siren.domain.User user){
        if(!user.isActivated()){
            throw new RuntimeException(email+" is not activated.");
        }
        List<GrantedAuthority> auth = user.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new User(user.getEmail(), user.getPassword(),auth);
    }
}
