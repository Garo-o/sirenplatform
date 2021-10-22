package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.Menu;
import com.ordersystem.siren.dto.MenuDto;
import com.ordersystem.siren.exception.MenuNotFoundException;
import com.ordersystem.siren.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public Menu addMenu(Menu menu){
        return menuRepository.save(menu);
    }
    @Transactional(readOnly = true)
    public Menu findOneByName(String name){
        return menuRepository.findByName(name).orElseThrow(()->new MenuNotFoundException("This "+name+" has not FOUND."));
    }
    @Transactional(readOnly = true)
    public Menu findOneById(Long id){
        return menuRepository.findById(id).orElseThrow(()->new MenuNotFoundException("Menu "+id+" has not FOUND."));
    }
    @Transactional(readOnly = true)
    public List<Menu> findAll(){
        return menuRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Menu> findOKMenu(){
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menuRepository.findAll()) {
            if(menu.isOk()){
                result.add(menu);
            }
        }
        return result;
    }
    public void stopMenu(Long id){
        this.findOneById(id).stop();
    }
    public void startMenu(Long id){
        this.findOneById(id).start();
    }
    public void update(Long id, Menu menu) {
        Menu oneById = this.findOneById(id);
        oneById.setName(menu.getName());
        oneById.setPrice(menu.getPrice());
    }
    public void delete(Long id) {
        menuRepository.delete(this.findOneById(id));
    }
}
