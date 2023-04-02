package c.lone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailDto {

    /*
        이 Dto를 통해 DB에 주문메뉴를 JSON 타입으로 저장
        JSON 타입으로 저장하는 이는 한 메뉴에 대한 추가옵션의 경우 여러개가 존재할 수 있으므
        로 DB 테이블에 여러 개의 칼럼을 추가하는 방법은 옳지 않습니다.
        Object를 JSON타입으로 쉽게
     */
    private String orderNum;
    private String foodInfoJSON;
}
