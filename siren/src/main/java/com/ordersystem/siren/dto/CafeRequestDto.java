package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.Address;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CafeRequestDto {

    @Getter
    public static class CafeDto{
        private String name;
    }

    @Getter
    public static class InsertBranch{
        private Long cafeId;
        private BranchDto branch;
    }

    @Getter
    public static class BranchDto{
        private String name;
        private Address address;
    }

    @Getter
    public static class InsertMenu{
        private Long cafeId;
        private MenuDto menu;
    }
    @Getter
    public static class MenuDto {
        private String name;
        private Long price;
        private ImageDto image;
    }

    @Getter
    public static class ImageDto{
        private String name;
        private MultipartFile imageFile;
    }
}

