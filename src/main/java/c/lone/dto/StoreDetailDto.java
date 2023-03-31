package c.lone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreDetailDto {

    /*
        매장의 상세화면에는 매장의 정보 뿐 아니라 메뉴, 리뷰, 별점등과 같은 정보가 나타나야하는데 각각의 테이블을 조회하는 것은 좋은 방법이 아니고,
        하나의 매장은 다수의 리뷰와 메뉴를 가지므로 매장의 ID값을 외래키로 다른 테이블을 join하여 정보를 끌어옴
    */
    private StoreDto storeInfo;

    //메뉴 목록
    private List<FoodDto> foodList;
}
