package com.ordersystem.siren.repository;

import com.ordersystem.siren.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    public Optional<Menu> findByName(String name);
}
