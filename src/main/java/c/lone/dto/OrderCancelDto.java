package c.lone.dto;

import lombok.Data;

@Data
public class OrderCancelDto {

    /*
        외부결제 API를 추가하여 사용할 때 주문취소를 하면 외부결제 API를 통해 결제된 내역 또한 취소해야 하므로 정보를 남기기 위함
     */
    private String orderNum;
    private long userId;
    private int totalPrice;
    private int usedPrice;
    private int deliveryTip;
    private String impUid; //아임포트 결제번호
}
