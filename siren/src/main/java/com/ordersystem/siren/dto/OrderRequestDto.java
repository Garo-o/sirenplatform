package com.ordersystem.siren.dto;

import lombok.Getter;

import java.util.List;

public class OrderRequestDto {
    @Getter
    public static class CreateOrder{
        private Long cafeId;
        private Long branchId;
        private List<MenuDto> menuDtos;
        private String memo;
    }
    
    @Getter
    public static class MenuDto{
        private Long menuId;
        private Long count;
        private Long price;
    }
}
