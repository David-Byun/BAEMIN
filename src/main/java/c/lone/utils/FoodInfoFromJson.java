package c.lone.utils;

import c.lone.dto.CartDto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/*
    gson 사용해서 하나의 주문에 포함된 모든 메뉴의 정보를 List<CartDto> 형태로 변환
    주문목록의 경우 하나의 주문이 아닌 지금까지의 모든 주문 정보를 표시해야하기 때문에 List<CartDto>를 포함하는 List 필요
    List<List<CartDto>> 2차원 리스트 사용
 */
public class FoodInfoFromJson {

    public static List<CartDto> foodInfoFromJson(String foodInfoJSON) {
        String[] arr = foodInfoJSON.split("/");
        Gson gson = new Gson();

        List<CartDto> cartDtoList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            //json => CartDto 객체로 변환해서 cartDtoList에 추가
            cartDtoList.add(gson.fromJson(arr[i], CartDto.class));

        }
        return cartDtoList;
    }
}
