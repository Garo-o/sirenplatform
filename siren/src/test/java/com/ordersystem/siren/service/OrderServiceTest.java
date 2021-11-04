package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.*;
import com.ordersystem.siren.dto.CafeRequestDto;
import com.ordersystem.siren.dto.OrderRequestDto;
import com.ordersystem.siren.dto.UserRequestDto;
import com.ordersystem.siren.jwt.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired private OrderService orderService;
    @Autowired private CafeService cafeService;
    @Autowired private UserService userService;

    @BeforeEach
    public void setUp(){
        User user = userService.findByEmail("admin");
        Branch branch = Branch.createBranch("번동", new Address("city", "state","street", "zip"));
        Cafe cafe = cafeService.addCafe(user, "Starbucks", branch);

        CafeRequestDto.MenuDto menuDto = CafeRequestDto.MenuDto.builder().name("iced americano").price(3500L).build();

        CafeRequestDto.InsertMenu insertMenu = CafeRequestDto.InsertMenu.builder().cafeId(cafe.getId()).menu(menuDto).build();
        cafeService.addMenu(insertMenu);
    }


    @Test
    public void 주문하기() throws Exception{
        //given
        List<OrderRequestDto.MenuDto> menuDtos = new ArrayList<>();
        OrderRequestDto.MenuDto menuDto= OrderRequestDto.MenuDto.builder().menuId(1L).count(5L).price(3500L).build();
        menuDtos.add(menuDto);
        OrderRequestDto.CreateOrder createOrder= OrderRequestDto.CreateOrder.builder().branchId(1L).cafeId(1L).menuDtos(menuDtos).memo("").build();
        User user = userService.findByEmail("admin");
        //when
        Order order = orderService.createOrder(createOrder, user);
        //then
        System.out.println("");
        System.out.println("orderDate: "+order.getOrderDate());
        for (OrderMenu orderMenu : order.getOrderMenus()) {
            System.out.println("    menu name: "+orderMenu.getMenu().getName());
            System.out.println("    count: "+orderMenu.getCount());
            System.out.println("    price: "+orderMenu.getTotalPrice());
        }
        System.out.println("Cafe id: "+order.getBranch().getCafe().getName());
        System.out.println("Branch id: "+order.getBranch().getName());
    }

}