package c.lone.dto;

import lombok.Data;

@Data
public class StoreDto {

    private long id;
    private int category;
    private String storeName;
    private int storeAddress1;
    private String storeAddress2;
    private String storeAddress3;
    private String storePhone;
    private String storeImg;
    private String storeThumb;
    private int openingTime;
    private int closingTime;
    private int minDelivery;
    private int deliveryTime;
    private int deliveryTip;
    private String storeDes;

    //매장의 상세정보 화면에서 리뷰가 몇개인지 평점 평균이 몇점인지 사장님 댓글이 몇개인지 나타내기 위한 작업
    private float score;
    private int orderCount;
    private int reviewCount;
    private int bossCommentCount;
    private int likesCount;

    //영업중
    private String isOpen;

    //리뷰 1~5점
    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;

    //찜 상태
    private int isLikes;


}
