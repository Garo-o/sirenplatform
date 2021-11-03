package com.ordersystem.siren.repository;

import com.ordersystem.siren.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    @Query("select distinct c from Cafe c left join c.menus m join fetch c.branches b where c.id = :id")
    Optional<Cafe> findById(@Param("id") Long id);

    @Query("select distinct c from Cafe c left join c.menus m join fetch c.branches b")
    List<Cafe> findAll();
}
