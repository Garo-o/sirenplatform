package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private Long id;
    @Column(nullable = false)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Address address;


    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Menu> menus= new ArrayList<>();
}
