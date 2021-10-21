package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    private MenuState menuState;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafeName")
    private Cafe cafe;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="image")
    private MenuImage image;

    //== 연관 관계 ==//
    public void addImage(MenuImage image){
        this.setImage(image);
        image.setMenu(this);
    }

    //== 생성자 ==//
    public static Menu createMenu(String name, Long price){
        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuState(MenuState.OK);

        return menu;
    }

    //== 비즈니스 ==//

    /**
     * 판매 중단
     */
    public void stop(){
        if(this.getMenuState() == MenuState.SOLD_OUT){
            throw new IllegalStateException("This menu has already SOLD-OUT.");
        }
        this.setMenuState(MenuState.SOLD_OUT);
    }

    /**
     * 판매 재가동
     */
    public void start(){
        if(this.getMenuState()== MenuState.OK){
            throw new IllegalStateException("This menu has already OK.");
        }
        this.setMenuState(MenuState.OK);
    }

    /**
     * 판매가능 확인
     */
    public void isOk() {
        if(this.getMenuState()==MenuState.SOLD_OUT){
            throw new IllegalStateException("This menu has SOLD OUT");
        }
    }
}
