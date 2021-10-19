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
    @Column(name = "cafeId")
    private Long id;
    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Menu> menus= new ArrayList<>();
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();
}
