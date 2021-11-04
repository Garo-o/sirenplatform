package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.*;
import com.ordersystem.siren.dto.OrderRequestDto;
import com.ordersystem.siren.exception.MenuNotFoundException;
import com.ordersystem.siren.exception.OrderNotFoundException;
import com.ordersystem.siren.repository.CafeRepository;
import com.ordersystem.siren.repository.MenuRepository;
import com.ordersystem.siren.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CafeService cafeService;

    @Transactional
    public Order createOrder(OrderRequestDto.CreateOrder createOrder, User user){
        Map<Long, Menu> menus = getOrderMenus(createOrder);
        Map<Long, Branch> branch = getOrderBranches(createOrder);

        List<OrderMenu> orderMenus = createOrder.getMenuDtos()
                .stream().map(m -> OrderMenu.createOrderMenu(menus.get(m.getMenuId()),m.getPrice(),m.getCount()))
                .collect(Collectors.toList());
        return orderRepository.save(Order.createOrder(user, orderMenus, branch.get(createOrder.getBranchId())));
    }

    @Transactional
    public boolean orderAccept(Long orderId){
        Order order = this.findOne(orderId);
        return order.ok();
    }

    @Transactional
    public boolean orderComp(Long orderId){
        Order order = this.findOne(orderId);
        return order.complete();
    }

    @Transactional
    public boolean orderCancle(Long orderId){
        Order order = this.findOne(orderId);
        return order.cancle();
    }


    private Map<Long,Menu> getOrderMenus(OrderRequestDto.CreateOrder createOrder){
        Cafe cafe = cafeService.findById(createOrder.getCafeId());
        Map<Long, Menu> menuMap = new HashMap<>();
        for (Menu menu : cafe.getMenus()) {
            menuMap.put(menu.getId(), menu);
        }

        Map<Long, Menu> resMenus = new HashMap<>();
        for (OrderRequestDto.MenuDto menuDto : createOrder.getMenuDtos()) {
            Long key = menuDto.getMenuId();
            if(menuMap.containsKey(key)){
                resMenus.put(key, menuMap.get(key));
            }
            else throw new MenuNotFoundException("해당 카페에 존재하지 않는 메뉴입니다.");
        }
        return resMenus;
    }

    private Map<Long, Branch> getOrderBranches(OrderRequestDto.CreateOrder createOrder) {
        Cafe cafe = cafeService.findById(createOrder.getCafeId());
        Map<Long, Branch> resBranches = new HashMap<>();
        for (Branch branch : cafe.getBranches()) {
            resBranches.put(branch.getId(),branch);
        }
        return resBranches;
    }

    public Order findOne(Long id){
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order not found."));
    }
}
