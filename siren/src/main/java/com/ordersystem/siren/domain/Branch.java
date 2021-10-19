package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branchId")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Embedded
    @Column(nullable = false)
    private Address address;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafeName")
    private Cafe cafe;
}
