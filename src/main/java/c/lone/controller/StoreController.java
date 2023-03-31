package c.lone.controller;

import c.lone.dto.StoreDetailDto;
import c.lone.dto.StoreDto;
import c.lone.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
    사용자가 메인화면에서 메뉴 클릭시 store/100/13541와 같은 주소로 이동
    @PathVariable을 사용하여 카테고리코드와 우편번호를 파라미터로 받음
    DB에서 내 주위 매장 목록을 조회하기 위해 사용
 */
@Controller
public class StoreController {

    StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/store/{category}/{address1}")
    public String store(@PathVariable int category, @PathVariable int address1, Model model) {

        List<StoreDto> storeList = storeService.storeList(category, address1 / 100);
        model.addAttribute("storeList", storeList
        );
        return "store/store";
    }

    @GetMapping("/store/{id}/detail")
    public String storeDetail(@PathVariable long id, Model model) {
        StoreDetailDto storeDetailDto = storeService.storeDetail(id);
        model.addAttribute("store", storeDetailDto);
        return "store/detail";
    }

}
