package com.ordersystem.siren.repository;

import com.ordersystem.siren.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
