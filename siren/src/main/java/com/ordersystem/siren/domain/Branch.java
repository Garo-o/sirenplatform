package com.ordersystem.siren.domain;

import com.ordersystem.siren.dto.CafeResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    private LocalDate regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafeId")
    private Cafe cafe;

    //== 생성자 ==//
    public static Branch createBranch(String name, Address address){
        Branch branch = new Branch();
        branch.setName(name);
        branch.setAddress(address);
        branch.setRegDate(LocalDate.now());
        return branch;
    }

    //== 응답DTO 생성자==//
    public CafeResponseDto.BranchDto toResponseBranchDto(){
        return new CafeResponseDto.BranchDto(this);
    }
}
