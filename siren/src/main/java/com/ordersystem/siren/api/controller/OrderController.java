package com.ordersystem.siren.api.controller;

import com.ordersystem.siren.domain.Order;
import com.ordersystem.siren.domain.User;
import com.ordersystem.siren.dto.OrderRequestDto;
import com.ordersystem.siren.dto.OrderResponseDto;
import com.ordersystem.siren.jwt.JwtFilter;
import com.ordersystem.siren.jwt.JwtTokenProvider;
import com.ordersystem.siren.repository.OrderRepository;
import com.ordersystem.siren.service.OrderService;
import com.ordersystem.siren.service.UserService;
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
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = {"3. Order"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final Response response;
    private final ErrorUtil errorUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "주문하기")
    @PostMapping("/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto.CreateOrder createOrder, HttpServletRequest request, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errorUtil.refineErrors(errors));
        }
        Authentication auth = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        User user = userService.findByEmail(auth.getName());

        Order order = orderService.createOrder(createOrder, user);
        return response.success(new OrderResponseDto.OrderDto(order),"주문이 완료 되었습니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "주문 수락")
    @PostMapping("/accept")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<?> acceptOrder(@Valid @RequestBody OrderRequestDto.OrderStatus orderStatus){
        if(orderService.orderAccept(orderStatus.getOrderId())){
            return response.success("주문 수락");
        }
        return response.fail("수락할 수 없는 상태입니다.",HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "제조 완료")
    @PostMapping("/ready")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<?> readyOrder(@Valid @RequestBody OrderRequestDto.OrderStatus orderStatus){
        if(orderService.orderComp(orderStatus.getOrderId())){
            return response.success("음료 제조 완료.");
        }
        return response.fail("완료할 수 없는 상태입니다.",HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "판매자가 주문 취소")
    @PostMapping("/cancle")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<?> cancleOrder(@Valid @RequestBody OrderRequestDto.OrderStatus orderStatus){
        if(orderService.orderCancle(orderStatus.getOrderId())){
            return response.success("주문 취소");
        }
        return response.fail("취소할 수 없는 상태입니다.",HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "구매자가 주문 취소 요청")
    @PostMapping("/request-cancle")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<?> requestCancleOrder(@Valid @RequestBody OrderRequestDto.OrderStatus orderStatus){
        // 구현해야함
        return response.success();
    }
}
