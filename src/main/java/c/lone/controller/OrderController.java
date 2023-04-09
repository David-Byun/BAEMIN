package c.lone.controller;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.CartDto;
import c.lone.dto.CartListDto;
import c.lone.dto.OrderListDto;
import c.lone.service.OrderService;
import c.lone.utils.CreateOrderNum;
import c.lone.utils.FoodInfoFromJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{storeId}")
    public String order(@PathVariable long storeId, HttpSession session, Model model) {
        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
        model.addAttribute("cartList", cartListDto);

        String orderNum = CreateOrderNum.createOrderNum();
        model.addAttribute("orderNum", orderNum);
        return "order/order";
    }

    @GetMapping("/orderList")
    public String orderList(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        if (principal == null) {
            System.out.println("비로그인");
        } else {
            System.out.println("로그인");
            int userId = principal.getId();
            List<OrderListDto> orderList = orderService.orderList(userId);
        }
        return null;
    }

    /*
       주문 상세페이지에는 본인이 요청한 주문에만 접근할 수 있어야 함.
       다른 사람의 주문내역에 접근하면 안되므로 사용자와 테이블에서 가져온 사용자가 일치하는지를 확인
       주문 목록에서와 마찬가지로 주문메뉴에 대한 정보를 JSON -> Object로 변환하여 model에 심어 화면 뿌려줌
     */
    @GetMapping("/orderDetail/{orderNum}")
    public String orderDetail(@PathVariable String orderNum, Model model, @AuthenticationPrincipal CustomUserDetails principal) {
        OrderListDto orderListDto = orderService.orderListDetail(orderNum);

        if (principal != null && (principal.getId() != orderListDto.getUserId())) {
            System.out.println("다른 사용자");
            return "redirect:/";
        } else if (principal == null) {
            System.out.println("비로그인");
            return "redirect:/";
        }

        List<CartDto> cartDtoList = FoodInfoFromJson.foodInfoFromJson(orderListDto.getFoodInfo());

        model.addAttribute("orderDetail", orderListDto);
        model.addAttribute("cart", cartDtoList);

        return "order/orderDetail";
    }
}
/*
    주문번호를 최종주문시가 아닌 주문페이지에서 미리 생성하는 이유는,
    단순 포인트만 사용해서 결제를 할 경우 포인트 사용 > 포인트 확인 > 포인트 차감 > 주문완료와 동시에 주문번호 생성해도 상관 없음
    다만 외부 서비스를 이용하여 결제 진행시 주문 완료보다 결제완료가 먼저 진행되기 때문에 이 외부서비스에서는 주문번호를 알 수 없음
    만약 고객이 주문취소를 한다고 했을 때 외부서비스는 주문번호를 저장해두지 않았기 때문에 완료된 결제목록 중 어느 것을 취소해야 하는지 알 수 없음
    그렇기 때문에 주문페이지 접근시 미리 주문번호를 생성해줘야 함
 */