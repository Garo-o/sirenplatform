package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //== 연관 관계 ==//
    public void setUser(User user){
        this.user= user;
        user.getOrders().add(this);
    }
    public void addOrderMenu(OrderMenu orderMenu){
        this.orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    //== 생성자 ==//
    public static Order createOrder(User user, OrderMenu... orderMenus){
        Order order = new Order();
        order.setUser(user);
        for (OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.ORDER);
        return order;
    }

    //== 비즈니스 ==//

    /**
     * 주문 승락
     */
    public void ok(){
        if(this.orderStatus!=OrderStatus.ORDER){
            throw new IllegalStateException("OrderState is not \"ORDER\"");
        }
        this.setOrderStatus(OrderStatus.OK);
    }

    /**
     * 주문 취소
     */
    public void cancle(){
        if(this.orderStatus!=OrderStatus.ORDER){
            throw new IllegalStateException("This order can't cancle");
        }
        this.setOrderStatus(OrderStatus.CANCLE);
    }

    /**
     * 제조 완료
     */
    public void complete(){
        if(this.orderStatus!=OrderStatus.OK){
            throw new IllegalStateException("OrderState is not \"OK\"");
        }
        this.setOrderStatus(OrderStatus.COMP);
    }

    /**
     * 전체 가격 조회
     * @return 전체 가격
     */
    public Long getTotalPrice(){
        Long totalPrice =0L;
        for (OrderMenu orderMenu : this.orderMenus) {
            totalPrice+=orderMenu.getTotalPrice();
        }
        return totalPrice;
    }
}
