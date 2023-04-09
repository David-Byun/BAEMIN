package c.lone.dto;

import lombok.Data;

import java.util.Date;

/*
    특정기간 총매출액 전달할 객체. 관리자가 7월 한달간의 매출액을 나타내길 원할시 orderDate는 7.1~7.31일까지 들어가게 되며
    total에는 각날의 총매출액이 저장됨. 제일 마지막에는 7.1~7.31일까지 모든 total을 더한 값이 저장됨.
    즉 List<salesDto>는 길이가 32인 리스트가 됨.
 */
@Data
public class SalesDto {
    private Date orderDate;
    private int total;
}
