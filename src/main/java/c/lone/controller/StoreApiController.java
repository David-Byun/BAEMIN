package c.lone.controller;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.FoodOptionDto;
import c.lone.dto.ReviewDto;
import c.lone.dto.StoreDto;
import c.lone.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.Store;
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


    // 리뷰 작성
    @PostMapping("/api/review")
    public ResponseEntity<String> reviewWrite(ReviewDto reviewDto, @AuthenticationPrincipal CustomUserDetails principal) {
        reviewDto.setUserId(principal.getId());
        if (storeService.reviewWrite(reviewDto)) {
            return ResponseEntity.ok().body("리뷰 작성 완료");
        }
        return ResponseEntity.badRequest().body("파일 저장 실패");

    }

    // 리뷰 수정
    @PutMapping("/api/review")
    public ResponseEntity<String> reviewModify(ReviewDto reviewDto, @AuthenticationPrincipal CustomUserDetails principal) {
        reviewDto.setUserId(principal.getId());
        if (storeService.reviewModify(reviewDto)) {
            return ResponseEntity.ok().body("리뷰 수정 완료");
        }
        return ResponseEntity.badRequest().body("파일 저장 실패");
    }

    // 매장 목록 페이징(무한스크롤 비동기식)
    @GetMapping("/api/store/storeList")
    public ResponseEntity<List<StoreDto>> storeStore(int category, int address1, String sort, int page) {
        List<StoreDto> storeList = storeService.storeList(category, address1 / 100, sort, page);
        return ResponseEntity.ok().body(storeList);
    }

    //찜하기
    @PostMapping("/api/store/likes")
    public ResponseEntity<String> likes(long id, String likes, @AuthenticationPrincipal CustomUserDetails principal) {
        //사용자가 로그인하지 않았다면 body에 메시지를 넣어 badRequest 응답
        if (principal == null) {
            return ResponseEntity.badRequest().body("회원만 가능합니다!");
        }

        //세션으로부터 userId 도출
        int userId = principal.getId();
        storeService.likes(id, likes, userId);
        return ResponseEntity.ok().body("완료");
    }

    //찜목록페이지 접근시 11번 이후 데이터
    @GetMapping("/api/store/likesList")
    public ResponseEntity<List<StoreDto>> likesStore(@AuthenticationPrincipal CustomUserDetails principal, int page) {
        List<StoreDto> storeList = storeService.likesList(principal.getId(), page);
        return ResponseEntity.ok().body(storeList);
    }

    /*
        매장검색 페이지 무한스크롤을 통한 페이징처리
        매장검색시 최초 1~10개 데이터는 Controller를 타며 그 이후 데이터는 Ajax를 이용해 ApiController를 타고 비동기적으로 화면에 데이터 뿌려줌
     */
    @GetMapping("/api/store/storeSearch")
    public ResponseEntity<List<StoreDto>> storeSearch(String keyword, int address1, int page) {
        List<StoreDto> storeList = storeService.storeSearch(keyword, address1 / 100, page);
        return ResponseEntity.ok().body(storeList);
    }
}
























