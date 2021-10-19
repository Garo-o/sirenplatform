package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="image")
    private MenuImage image;


    private Boolean soldOut = false;



    public void stopSelling(){this.soldOut=true;}
    public void changeName(String name){this.name=name;}
    public void changePrice(Long price){this.price=price;}
}
