package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.Address;
import com.ordersystem.siren.domain.Branch;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CafeRequestDto {

    @Getter
    @Builder
    public static class CafeDto{
        private Long userId;
        private String name;
        private BranchDto branchDto;
    }

    @Getter
    @Builder
    public static class InsertBranch{
        private Long userId;
        private Long cafeId;
        private BranchDto branch;
    }

    @Getter
    @Builder
    public static class BranchDto{
        private String name;
        private Address address;

        public Branch toEntity() {
            return Branch.createBranch(this.getName(), this.getAddress());
        }
    }

    @Getter
    @Builder
    public static class InsertMenu{
        private Long cafeId;
        private MenuDto menu;
    }
    @Getter
    @Builder
    public static class MenuDto {
        private String name;
        private Long price;
        private ImageDto image;
    }

    @Getter
    @Builder
    public static class ImageDto{
        private String name;
        private MultipartFile imageFile;
    }
}

