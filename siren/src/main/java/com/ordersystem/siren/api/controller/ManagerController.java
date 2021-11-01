package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.domain.Menu;
import com.ordersystem.siren.dto.CafeRequestDto;
import com.ordersystem.siren.service.CafeService;
import com.ordersystem.siren.util.ErrorUtil;
import com.ordersystem.siren.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
@RequestMapping("/api/v1/manager")
@Api(tags = {"2. Cafe"})
public class ManagerController {
    private final CafeService cafeService;
    private final Response response;
    private final ErrorUtil errorUtil;

    @ApiOperation(value = "카페 추가")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/cafe/add")
    public ResponseEntity<?> addCafe(@RequestBody @Valid CafeRequestDto.CafeDto cafeDto, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }

        return response.success(cafeService.addCafe(cafeDto), "카페 등록이 완료 되었습니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "지점 추가")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/branch/add")
    public ResponseEntity<?> addBranch(@RequestBody @Valid CafeRequestDto.InsertBranch branchDto, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }

        return response.success(cafeService.addBranch(branchDto), "지점 등록이 완료 되었습니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴 추가")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/menu/add")
    public ResponseEntity<?> addMenu(@RequestBody @Valid CafeRequestDto.InsertMenu menuDto, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }
        return response.success(cafeService.addMenu(menuDto), "메뉴 등록이 완료 되었습니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/menu/update/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable("id") Long id,@RequestBody @Valid CafeRequestDto.InsertMenu menuDto, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }

//        menuService.update(id, );
        return response.success();
    }

    @ApiOperation(value = "전체 메뉴 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/menu")
    public ResponseEntity<?> getAllMenu(@RequestBody @Valid Long cafeId, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }
        return response.success(cafeService.findAllMenu(cafeId), "All menus", HttpStatus.OK);
    }

    @ApiOperation(value = "판매 가능한 전체 메뉴 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/menu/ok")
    public ResponseEntity<?> getAllOKMenu(@RequestBody @Valid Long cafeId, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }
        return response.success(cafeService.findAllOkMenu(cafeId), "All ok menus", HttpStatus.OK);
    }

    @ApiOperation(value = "단일 메뉴 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/menu/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id, @RequestBody @Valid Long cafeId, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }

        Menu menu = cafeService.findMenuById(id, cafeId);
        if(menu == null){
            response.fail("",HttpStatus.BAD_REQUEST);
        }
        return response.success(menu, id+"", HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴 판매 중단")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/menu/stop/{id}")
    public ResponseEntity<?> stopMenu(@PathVariable("id") Long id, @RequestBody @Valid Long cafeId, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }

        if(cafeService.stopMenu(id, cafeId)) {
            return response.success("menu stopped");
        }
        return response.fail("menu already stopped", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "메뉴 판매 가동")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/menu/start/{id}")
    public ResponseEntity<?> startMenu(@PathVariable("id") Long id, @RequestBody @Valid Long cafeId, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }

        if(cafeService.startMenu(id, cafeId)) {
            return response.success("menu started");
        }
        return response.fail("menu already started", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "모든 카페 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllCafe(){
        return response.success(cafeService.findAllCafe(), "모든 카페 조회 내역입니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "단일 카페 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCafe(@PathVariable("id") Long id){
        return response.success(cafeService.findById(id), "모든 카페 조회 내역입니다.", HttpStatus.OK);
    }
}
