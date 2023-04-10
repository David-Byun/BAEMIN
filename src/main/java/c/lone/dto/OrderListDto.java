package c.lone.dto;

import lombok.Data;

import java.util.Date;

/*
    배달 주문시에는 회원/비회원으로 나뉘기 때문에 회원일때만 주문목록에 기존 주문내역 표출
    주문내역에는 회원이 주문한 정보 뿐 아니라 매장의 몇가지 정보도 필요
    OrderInfo, OrderDetail, Store 세개 테이블에 저장해놓은 정보를 join해서 가져올 것이기 때문에 Dto 새로 추가

    Mapper.xml의 resultType 형태로 해당 DTO로 데이터를 받아옴
 */
@Data
public class OrderListDto {
    private String orderNum;
    private long storeId;
    private long userId;
    private Date orderDate;
    private String DeliveryStatus;
    private int DeliveryAddress1;
    private String DeliveryAddress2;
    private String DeliveryAddress3;
    private String payMethod;
    private int totalPrice;
    private int usedPoint;
    private String phone;
    private String request;
    private String foodInfo;
    private String storeName;
    private String storeImg;
    private String storeThumb;
    private String deliveryTip;

    private String impUid;
}