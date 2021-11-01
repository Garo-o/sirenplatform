package com.ordersystem.siren.domain;

import com.ordersystem.siren.dto.CafeResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Set<Branch> branches = new HashSet<>();

    private LocalDate regDate;

    //== 연관 관계 ==//
    public void addMenu(Menu menu){
        this.menus.add(menu);
        menu.setCafe(this);
    }
    public void addBranch(Branch branch){
        this.branches.add(branch);
        branch.setCafe(this);
    }

    //== 생성자 ==//
    public static Cafe createCafe(String name){
        Cafe cafe = new Cafe();
        cafe.setName(name);
        cafe.setRegDate(LocalDate.now());
        return cafe;
    }
    //== 응답DTO 생성자==//
    public CafeResponseDto.CafeDto toResponseCafeDto(){
        return new CafeResponseDto.CafeDto(this);
    }
}
