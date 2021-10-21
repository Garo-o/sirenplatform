package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    private Long orderPrice;
    private Long count;

    //== 생성자 ==//
    public static OrderMenu createOrderMenu(Menu menu, Long orderPrice, Long count){
        OrderMenu orderMenu = new OrderMenu();
        menu.isOk();

        orderMenu.setMenu(menu);
        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);
        return orderMenu;
    }
    //== 비즈니스 ==//

    /**
     * 전체 가격 조회
     * @return 전체 가격
     */
    public Long getTotalPrice(){return getOrderPrice()*getCount();}
}
