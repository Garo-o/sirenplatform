package com.ordersystem.siren.repository;

import com.ordersystem.siren.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
