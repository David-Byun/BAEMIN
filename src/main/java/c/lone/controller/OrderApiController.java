package c.lone.controller;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.CartListDto;
import c.lone.dto.OrderInfoDto;
import c.lone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class OrderApiController {

    OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    //@AuthenticationPrincipal 어노테이션 이용하여 로그인 세션 받아오기
    @PostMapping("/api/order/payment-cash")
    public ResponseEntity<String> payment(HttpSession session, OrderInfoDto orderInfoDto, long totalPrice, @AuthenticationPrincipal CustomUserDetails principal) {
        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");

        long orderPriceCheck = orderService.orderPriceCheck(cartListDto);
        if (orderPriceCheck == totalPrice) {
            orderService.order(cartListDto, orderInfoDto, principal, session);
        }
        return null;
    }
}
