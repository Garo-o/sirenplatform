package com.ordersystem.siren.repository;

import com.ordersystem.siren.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByName(String name);
}
