package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderResponseDto {
    @ToString
    @Getter
    public static class OrderDto{
        private Long id;
        private LocalDate orderDate;
        private OrderStatus orderStatus;
        private OrderResponseDto.BranchDto branch;
        private List<OrderResponseDto.OrderMenuDto> orderMenus;

        public OrderDto(Order order){
            this.id = order.getId();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getOrderStatus();
            this.branch = new BranchDto(order.getBranch());
            this.orderMenus = new ArrayList<>();
            for (OrderMenu orderMenu : order.getOrderMenus()) {
                this.orderMenus.add(new OrderMenuDto(orderMenu));
            }
        }
    }

    @Getter
    @ToString
    public static class BranchDto{
        private Long id;
        private String name;
        private Address address;

        public BranchDto(Branch branch){
            this.id = branch.getId();
            this.name = branch.getName();
            this.address = branch.getAddress();
        }
    }
    @Getter
    @ToString
    public static class OrderMenuDto{
        private Long id;
        private OrderResponseDto.MenuDto menu;
        private Long totalPrice;
        private Long count;

        public OrderMenuDto(OrderMenu oderMenu){
            this.id = oderMenu.getId();
            this.menu = new MenuDto(oderMenu.getMenu());
            this.totalPrice = oderMenu.getTotalPrice();
            this.count = oderMenu.getCount();
        }
    }
    @Getter
    @ToString
    public static class MenuDto{
        private Long id;
        private String name;
        private Long price;

        public MenuDto(Menu menu){
            this.id=menu.getId();
            this.name=menu.getName();
            this.price=menu.getPrice();
        }
    }
}
