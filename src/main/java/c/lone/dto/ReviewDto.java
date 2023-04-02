package c.lone.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/*
    화면에 리뷰를 출력할 데이터
    MultipartFile file에 담긴 파일을 특정 폴더안에 저장시킨후 그 파일이 저장된 주소를 String reviewImg에 넣어줘야함
    스트링부트는 기본적으로 정적파일을 불러올 때 현재 패키지의 static폴더를 참조하기 때문에 view에서 img src="C:/delivery/upload/image.jpg" 식으로 불러오려고 해도 static폴더 안에서 해당 주소를 찾기 때문에 해당 이미지 파일을 불러올 수 없음.
    따라서 프로젝트 외부폴더를 참조할 수 있도록 설정 필

    WebMvcConfig.java 추가
 */

@Data
public class ReviewDto {
    private String orderNum;
    private long storeId;
    private String storeName;
    private String reviewContent;
    private String bossComment;
    private Date regiDate;
    private float score;
    private String reviewImg;
    private MultipartFile file;
    private long userId;
    private String username;
    private String nickname;
}
