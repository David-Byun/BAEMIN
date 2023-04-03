package c.lone.controller;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.StoreDetailDto;
import c.lone.dto.StoreDto;
import c.lone.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final StoreService storeService;

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

    /*
        기존 매장상세 정보를 가져올 때 현재 로그인된 사용자의 입장에서 찜을 한 가게인지 아닌지를 판단하여 화면에 찜 On, Off를 보여줘야하므로
        모든 레이어에서 기존 storeDetail에 userId를 추가
     */
    @GetMapping("/store/{id}/detail")
    public String storeDetail(@PathVariable long id, Model model, @AuthenticationPrincipal CustomUserDetails principal) {
        long userId = 0;
        if (principal != null) {
            userId = principal.getId();
        }
        StoreDetailDto storeDetailDto = storeService.storeDetail(id, userId);
        model.addAttribute("store", storeDetailDto);
        return "store/detail";
    }

    // 찜한 가게 목록(최초 찜목록 페이지 접근시 1~10번까지의 데이터)
    @GetMapping("/likes/store")
    public String likes(Model model, @AuthenticationPrincipal CustomUserDetails principal) {
        if (principal == null) {
            System.out.println("비로그인");
        } else {
            System.out.println("로그인");
            List<StoreDto> likesList = storeService.likesList(principal.getId());
            model.addAttribute("likesList", likesList);
        }
        return "/store/likes";
    }

    // 매장검색
    @GetMapping("/store/search")
    public String search(String keyword, Integer address1, Model model) {
        if (keyword != null) {
            List<StoreDto> storeList = storeService.storeSearch(keyword, address1 / 100);
            model.addAttribute("storeList", storeList);
            model.addAttribute("keyword", keyword);
        }
        return "/store/search";
    }
}
