package c.lone.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoDto {

    private String orderNum;
    private long storeId;
    private long userId;
    private Date orderDate;

    private String deleveryStatus;
    private int deleveryAddress1;
    private String deleveryAddress2;
    private String deleveryAddress3;
    private String payMethod;
    private int totalPrice;
    private int usedPoint;
    private String phone;
    private String request;

    private String impUid; //아임포트 결제번호 추가
}
