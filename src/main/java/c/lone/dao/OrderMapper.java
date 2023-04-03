package c.lone.dao;

import c.lone.dto.CartDto;
import c.lone.dto.OrderInfoDto;
import c.lone.dto.OrderListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    //메뉴 총합가격 계산시 배달팁 가져오기
    public int getDeliveryTip(long storeId);

    //메뉴 총합가격 계산시 음식가격
    public List<Integer> foodPriceList(List<CartDto> cartList);

    //메뉴 총합가격 계산시 음식 추가 옵션가격
    public List<Integer> optionPriceList(List<CartDto> cartList);

    //주문 정보 입력
    public void order(OrderInfoDto orderInfoDto);

    //주문 상세정보 입력
    public void orderDetail(Map<String, Object> map);

    //주문 목록 가져오기
    public List<OrderListDto> orderList(long userId);

    //주문목록 상세보기 페이지
    public OrderListDto orderListDetail(String orderNum);

    //주문 전체 목록 가져오기
}
