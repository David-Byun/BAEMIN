package c.lone.controller;

import c.lone.dto.CartDto;
import c.lone.dto.FoodDto;
import c.lone.dto.OrderListDto;
import c.lone.dto.StoreDto;
import c.lone.service.AdminService;
import c.lone.utils.FileUpload;
import c.lone.utils.FoodInfoFromJson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AdminApiController {
    private final AdminService adminService;
    private final FileUpload fileUpload;

    //메뉴추가
    @PostMapping("/api/admin/management/menu")
    public ResponseEntity<FoodDto> addMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice, MultipartFile file) {
        if (file.isEmpty()) {
            String img = File.separator + "img" + File.separator + "none.gif";
            foodDto.setFoodImg(img);
            foodDto.setFoodThumb(img);
        } else {
            String img = fileUpload.uploadImg(file, "foodImg");
            foodDto.setFoodImg(img);
            foodDto.setFoodThumb(img);
        }
        adminService.addMenu(foodDto, foodOption, foodOptionPrice);
        return ResponseEntity.ok().body(foodDto);
    }

    /*
        메뉴 추가의 경우 이미지를 등록했는지 안했는지 확인. 등록을 하지 않았을시 none 값을 가지게 하고 등록했을 경우
        foodImg폴더 안에 파일을 저장하고 그 주소를 FoodDto에 변수값으로 넣어줌
        메뉴 추가/수정 모두 한번에 메뉴 1가지만 추가/수정이 가능하지만 옵션의 경우 여러개를 추가하거나 수정할 수 있음
        따라서 이를 배열의 형태로 저장
     */
    @PatchMapping("/api/admin/management/menu")
    public ResponseEntity<FoodDto> updateMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId, MultipartFile file) {
        if (!file.isEmpty()) {
            String img = fileUpload.uploadImg(file, "foodImg");
            foodDto.setFoodThumb(img);
            foodDto.setFoodImg(img);
        }

        adminService.updateMenu(foodDto, foodOption, foodOptionPrice, optionId);
        return ResponseEntity.ok().body(foodDto);
    }

    //메뉴삭제
    @DeleteMapping("/api/admin/management/menu")
    public ResponseEntity<String> deleteMenu(long storeId, long[] deleteNumber) {
        adminService.deleteMenu(storeId, deleteNumber);
        return ResponseEntity.ok().body("삭제 완료");
    }

    //매장 정보 수정
    @PatchMapping("/api/admin/management/storeInfo")
    public ResponseEntity<StoreDto> storeInfoUpdate(StoreDto storeDto, MultipartFile file) {
        if (!file.isEmpty()) {
            String img = fileUpload.uploadImg(file, "storeImg");
            storeDto.setStoreThumb(img);
            storeDto.setStoreImg(img);
        }
        adminService.storeInfoUpdate(storeDto);
        return ResponseEntity.ok().body(storeDto);
    }

    //리뷰 답글 작성
    @PatchMapping("/api/admin/management/bossComment")
    public ResponseEntity<String> bossComment(long storeId, String orderNum, String bossComment) {
        String reviewContent = adminService.bossCommen회t(storeId, orderNum, bossComment);
        return ResponseEntity.ok().body(reviewContent);
    }

    /*
        주문접수 리스트는 주문관리페이지에서 주문대기/처리중/주문취소/배달완료 탭 클릭시 해당 탭에 맞는 정보를 뿌려주기 위한 메서드
        무한스크롤 페이징처리를 위해 page를 파라미터로 가지며 list에는 주문대기/처리중과 같은 키워드를 받아 테이블 검색
        일반 사용자의 주문목록 페이지에서와 마찬가지로 Json데이터를 object로 변환해주는 작업 후 이차원리스트 저장
     */
    @GetMapping("/api/admin/management/orderList")
    public ResponseEntity<Map<String, Object>> orderList(long storeId, String list, int page) {
        List<OrderListDto> orderListDto = adminService.orderList(storeId, list, page);

        Map<String, Object> map = new HashMap<>();
        List<List<CartDto>> menuList = new ArrayList<>();
        if (orderListDto.size() != 0 && orderListDto.get(0).getFoodInfo() != null) {
            for (int i = 0; i < orderListDto.size(); i++) {
                menuList.add(FoodInfoFromJson)
            }
        }
    }
}





























