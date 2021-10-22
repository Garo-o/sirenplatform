package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.domain.Menu;
import com.ordersystem.siren.dto.MenuDto;
import com.ordersystem.siren.repository.MenuRepository;
import com.ordersystem.siren.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/v1/add")
    public ResponseEntity<MenuDto> addMenu(@RequestBody @Valid MenuDto menuDto){
        menuService.addMenu(menuDto.toEntity());
        return ResponseEntity.ok(menuDto);
    }
    @PostMapping("/v1/update/{id}")
    public ResponseEntity<String> updateMenu(@PathVariable("id") Long id,@RequestBody @Valid MenuDto menuDto){
        menuService.update(id, menuDto.toEntity());
        return ResponseEntity.ok("OK");
    }
    @PostMapping("/v1/delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable("id") Long id){
        menuService.delete(id);
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/v1/")
    public ResponseEntity<List<Menu>> getAllMenu(){
        return ResponseEntity.ok(menuService.findAll());
    }
    @GetMapping("/v1/{id}")
    public ResponseEntity<Menu> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(menuService.findOneById(id));
    }
    @GetMapping("/v1/stop/{id}")
    public ResponseEntity<String> stopMenu(@PathVariable("id") Long id){
        menuService.stopMenu(id);
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/v1/start/{id}")
    public ResponseEntity<String> startMenu(@PathVariable("id") Long id){
        menuService.startMenu(id);
        return ResponseEntity.ok("OK");
    }
}
