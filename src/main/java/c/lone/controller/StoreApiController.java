package c.lone.controller;

import c.lone.dto.FoodOptionDto;
import c.lone.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreApiController {

    StoreService storeService;

    @Autowired
    public StoreApiController(StoreService storeService) {
        this.storeService = storeService;
    }

    // 메뉴 클릭시 음식 추가옵션 가져오기
    @GetMapping("/api/food/{foodId}/option")
    public List<FoodOptionDto> menuDetail(@PathVariable long foodId) {
        List<FoodOptionDto> foodOption = storeService.foodOption(foodId);
        return foodOption;
    }
}
