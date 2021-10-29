package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.repository.MenuRepository;
import com.ordersystem.siren.repository.OrderRepository;
import com.ordersystem.siren.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    
}
