package c.lone.dto;

import lombok.Data;

/*
    SalesDetailDto는 특정날짜의 판매한 주문들의 메뉴목록과 주문금액을 전달할 객체.
    7월31일 하루에 총 30건의 주문을 완료하였다면 List<SalesDetailDto>는 길이가 30인 리스트가 됨.
    총 금액의 경우 SalesDto와는 다르게 이 안에 저장하지는 않음
 */
@Data
public class SalesDetailDto {
    private int totalPrice;
    private String foodInfo;
}
