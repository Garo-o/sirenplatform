package com.ordersystem.siren.domain;

import com.ordersystem.siren.dto.CafeResponseDto;
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
    @JoinColumn(name = "cafeId")
    private Cafe cafe;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="imageId")
    private MenuImage image;

    //== 연관 관계 ==//
    public void addImage(MenuImage image){
        this.setImage(image);
        image.setMenu(this);
    }
    public void removeImage(MenuImage image){
        this.setImage(null);
        image.setMenu(null);
    }

    //== 생성자 ==//
    public static Menu createMenu(String name, Long price){
        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuState(MenuState.OK);

        return menu;
    }
    //== 응답DTO 생성자==//
    public CafeResponseDto.MenuDto toResponseMenuDto(){
        return new CafeResponseDto.MenuDto(this);
    }

    //== 비즈니스 ==//

    /**
     * 판매 중단
     */
    public boolean stop(){
        if(this.getMenuState() == MenuState.SOLD_OUT){
            return false;
        }
        this.setMenuState(MenuState.SOLD_OUT);
        return true;
    }

    /**
     * 판매 재가동
     */
    public boolean start(){
        if(this.getMenuState()== MenuState.OK){
            return false;
        }
        this.setMenuState(MenuState.OK);
        return true;
    }

    /**
     * 판매가능 확인
     */
    public boolean isOk() {
        if(this.getMenuState()==MenuState.SOLD_OUT){
            return false;
        }
        return true;
    }
}
