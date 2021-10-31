package com.ordersystem.siren.util;

import com.ordersystem.siren.domain.Menu;
import com.ordersystem.siren.domain.MenuImage;
import com.ordersystem.siren.dto.CafeRequestDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtil {
    private static final String FILE_PATH="siren//src//main//resources//static//image//";
    public String saveImage(String postPath, MultipartFile file) {
        String path = FILE_PATH+postPath;

        // 이미지 저장로직 추가 예정

        return path;
    }
}
