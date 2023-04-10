package c.lone.service;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dao.OrderMapper;
import c.lone.dto.*;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Transactional
    public long orderPriceCheck(CartListDto cartListDto) {
        List<CartDto> cart = cartListDto.getCartDto();
        List<Integer> foodPriceList = orderMapper.foodPriceList(cart);
        List<Integer> optionPriceList = orderMapper.optionPriceList(cart);
        int deliveryTip = orderMapper.getDeliveryTip(cartListDto.getStoreId());
        int sum = 0;
        for (int i = 0; i < cart.size(); i++) {
            int foodPrice = foodPriceList.get(i);
            int amount = cart.get(i).getAmount();
            int optionPrice = optionPriceList.get(i);

            sum += (foodPrice + optionPrice) * amount;
        }
        return sum + deliveryTip;
    }

    @Transactional
    public void order(CartListDto cartListDto, OrderInfoDto orderInfoDto, CustomUserDetails principal, HttpSession session) {
        Gson gson = new Gson();

        System.out.println("info = " + orderInfoDto);

        int total = cartListDto.getCartTotal();

        orderInfoDto.setStoreId(cartListDto.getStoreId());
        orderInfoDto.setTotalPrice(total);

        long userId = 0;
        if (principal != null) {
            userId = principal.getId();
            orderInfoDto.setUserId(userId);
        }

        List<CartDto> cartList = cartListDto.getCartDto();

        OrderDetailDto[] orderDetailDto = new OrderDetailDto[cartList.size()];

        for (int i = 0; i < orderDetailDto.length; i++) {
            String cartJSON = gson.toJson(cartList.get(i));
            orderDetailDto[i] = new OrderDetailDto(orderInfoDto.getOrderNum(), cartJSON);
        }

        orderMapper.order(orderInfoDto);

        Map<String, Object> orderDetailMap = new HashMap<>();
        orderDetailMap.put("userId", userId);
        orderDetailMap.put("detail", orderDetailDto);
        orderMapper.orderDetail(orderDetailMap);
    }

    public List<OrderListDto> orderList(long userId) {
        return orderMapper.orderList(userId);
    }

    //주문목록 상세보기
    public OrderListDto orderListDetail(String orderNum) {
        return orderMapper.orderListDetail(orderNum);
    }
}
