package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    private boolean activated;

    @ManyToMany
    @JoinTable(
            name="userAuthority",
            joinColumns = {@JoinColumn(name="userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name="authorityName", referencedColumnName = "authorityName")}
    )
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
