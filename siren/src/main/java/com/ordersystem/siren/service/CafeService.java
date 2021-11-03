package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.*;
import com.ordersystem.siren.dto.CafeRequestDto;
import com.ordersystem.siren.exception.CafeNotFoundException;
import com.ordersystem.siren.repository.CafeRepository;
import com.ordersystem.siren.repository.MenuRepository;
import com.ordersystem.siren.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {
    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final ImageUtil imageUtil;

    @Transactional
    public Cafe addCafe(User user, String name, Branch branch){
        Cafe cafe = Cafe.createCafe(user, name, branch);
        return cafeRepository.save(cafe);
    }
    @Transactional
    public Branch addBranch(CafeRequestDto.InsertBranch insertBranch){
        Cafe cafe = this.findById(insertBranch.getCafeId());
        CafeRequestDto.BranchDto branchDto = insertBranch.getBranch();
        Branch branch = Branch.createBranch(branchDto.getName(), branchDto.getAddress());
        cafe.addBranch(branch);
        return branch;
    }
    @Transactional
    public Menu addMenu(CafeRequestDto.InsertMenu insertMenu){
        Cafe cafe = this.findById(insertMenu.getCafeId());
        CafeRequestDto.MenuDto menuDto = insertMenu.getMenu();
        Menu menu = Menu.createMenu(menuDto.getName(), menuDto.getPrice());
        cafe.addMenu(menu);

        if(menuDto.getImage()!= null) {
            MultipartFile imageFile = menuDto.getImage().getImageFile();

            String postPath = cafe.getName() + cafe.getId().toString() + "//" + menu.getName();
            String path = imageUtil.saveImage(postPath, imageFile);

            MenuImage image = MenuImage.builder()
                    .fileName(menu.getName())
                    .filePath(path)
                    .fileSize(imageFile.getSize())
                    .build();
            menu.addImage(image);
        }
        return menu;
    }

    public Cafe findById(Long id){
        return cafeRepository.findById(id).orElseThrow(()-> new CafeNotFoundException("해당하는 카페가 존재하지 않습니다."));
    }

    public List<Menu> findAllMenu(){
        return menuRepository.findAll();
    }

    public List<Menu> findAllOkMenu(){
        List<Menu> okMenu = new ArrayList<>();

        for (Menu menu : this.findAllMenu()) {
            if(menu.isOk())okMenu.add(menu);
        }

        return okMenu;
    }
    public Menu findMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public boolean stopMenu(Long id) {
        Menu menu = findMenuById(id);
        if(menu == null || !menu.stop()) return false;
        return true;
    }
    @Transactional
    public boolean startMenu(Long id) {
        Menu menu = findMenuById(id);
        if(menu == null || !menu.start()) return false;
        return true;
    }

    public List<Cafe> findAllCafe() {
        return cafeRepository.findAll();
    }
}
