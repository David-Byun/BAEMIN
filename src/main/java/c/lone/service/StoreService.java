package c.lone.service;

import c.lone.dao.StoreMapper;
import c.lone.dto.*;
import c.lone.utils.FileUpload;
import c.lone.utils.Page;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final FileUpload fileUpload;

    /*
        리뷰를 작성할 때 사용자는 사진을 올릴 수도 있고 올리지 않을수도 있기 때문에
     */
    @Transactional
    public boolean reviewWrite(ReviewDto reviewDto) {
        if (reviewDto.getFile() == null) {
            reviewDto.setReviewImg("");
        } else {
            if (fileUpload.uploadReviewImg(reviewDto)) {
                return false;
            }
        }
        storeMapper.reviewWrite(reviewDto);
        return true;
    }

    // 리뷰 수정
    @Transactional
    public boolean reviewModify(ReviewDto reviewDto) {
        if (reviewDto.getFile() == null) {
            reviewDto.setReviewImg("");
        }
        else {
            if (!fileUpload.uploadReviewImg(reviewDto)) {
                return false;
            }
        }
        storeMapper.reviewModify(reviewDto);
        return true;
    }

    @Transactional
    public List<StoreDto> storeList(int category, int address) {
        return storeList(category, address, "기본 순", 1);
    }

    //페이징 처리를 위한 추가 로직
    @Transactional
    public List<StoreDto> storeList(int category, int address1, String sort, int page) {
        Page p = new Page(page, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("address1", address1);
        map.put("firstList", p.getFirstList());
        map.put("lastList", p.getLastList());
        map.put("sort", sort);
        System.out.println("페이지 시작 = " + p.getFirstList() + "페이지 끝 = " + p.getLastList());
        return storeMapper.storeList(map);

    }

    @Transactional
    public StoreDetailDto storeDetail(long storeId, long userId) {
        Map<String, Long> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("userId", userId);

        StoreDto storeDto = storeMapper.storeDetail(map);
        List<FoodDto> foodList = storeMapper.foodList(storeId);
        //추가
        List<ReviewDto> reviewList = storeMapper.reviewList(storeId);
        return new StoreDetailDto(storeDto, foodList, reviewList);
    }

    @Transactional
    public List<FoodOptionDto> foodOption(long foodId) {
        return storeMapper.foodOption(foodId);
    }

    @Transactional
    public void likes(long storeId, String likes, long userId) {
        Map<String, Long> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("userId", userId);
        if (likes.equals("on")) {
            storeMapper.addLikes(map);
        } else {
            storeMapper.deleteLikes(map);
        }
    }

    //찜한 가게 목록 첫 접근시
    public List<StoreDto> likesList(long userId) {
        return likesList(userId, 1);
    }

    //찜한 가게 목록 추가 데이터 비동기
    public List<StoreDto> likesList(long userId, int page) {
        Page p = new Page(page, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("firstList", p.getFirstList());
        map.put("lastList", p.getLastList());
        map.put("userId", userId);
        return storeMapper.likesList(map);
    }

    //매장 검색 무한 스크롤 페이징
    @Transactional
    public List<StoreDto> storeSearch(String keyword, int address1, int page) {
        Page p = new Page(page, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("address1", address1);
        map.put("firstList", p.getFirstList());
        map.put("lastList", p.getLastList());
        return storeMapper.storeSearch(map);
    }

    //매장 검색
    @Transactional
    public List<StoreDto> storeSearch(String keyword, int address) {
        return storeSearch(keyword, address, 1);
    }

}
























