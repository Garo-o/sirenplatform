package com.ordersystem.siren.dto;

import com.ordersystem.siren.domain.Menu;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenuDto {
    @NotNull
    private String name;
    @NotNull
    private Long price;

    public Menu toEntity(){
        return Menu.createMenu(this.getName(),this.getPrice());
    }
}
