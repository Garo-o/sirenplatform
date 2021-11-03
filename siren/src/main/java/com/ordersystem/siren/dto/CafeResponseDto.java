package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CafeResponseDto {
    @ToString
    public static class BranchDto{
        private Long id;
        private String name;
        private Address address;
        private LocalDate regDate;

        public BranchDto(Branch branch){
            this.id=branch.getId();
            this.name=branch.getName();
            this.address=branch.getAddress();
            this.regDate=branch.getRegDate();
        }
    }
    @ToString
    public static class CafeDto{
        private Long id;
        private String name;
        private LocalDate regDate;
        private List<BranchDto> branchDtos;
        private List<MenuDto> menuDtos;

        public CafeDto(Cafe cafe){
            this.id=cafe.getId();
            this.name= cafe.getName();;
            this.regDate=cafe.getRegDate();
            this.branchDtos=cafe.getBranches().stream().map(b->new BranchDto(b)).collect(Collectors.toList());
            this.menuDtos = cafe.getMenus().stream().map(m->new MenuDto(m)).collect(Collectors.toList());
        }
    }
    @ToString
    public static class MenuDto{
        private Long id;
        private String name;
        private Long price;
        private String menuState;
        private ImageDto imageFile = null;

        public MenuDto(Menu menu){
            this.id = menu.getId();
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.menuState = menu.getMenuState().toString();
            if(menu.getImage() != null) {
                this.imageFile = new ImageDto(menu.getImage());
            }
        }
    }
    @ToString
    public static class ImageDto{
        private Long id;
        private String fileName;
        private String filePath;
        private Long fileSize;

        public ImageDto(MenuImage image){
            this.id=image.getId();
            this.fileName=image.getFileName();
            this.filePath=image.getFilePath();
            this.fileSize=image.getFileSize();
        }
    }
}
