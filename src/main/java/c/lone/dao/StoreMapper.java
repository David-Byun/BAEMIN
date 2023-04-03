package c.lone.dao;

import c.lone.dto.FoodDto;
import c.lone.dto.FoodOptionDto;
import c.lone.dto.ReviewDto;
import c.lone.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StoreMapper {
    public List<StoreDto> storeList(int category, int address);

    public StoreDto storeDetail(Map<String, Long> map);

    public List<FoodDto> foodList(long storeId);

    public List<FoodOptionDto> foodOption(long foodId);

    //리뷰 작성
    public void reviewWrite(ReviewDto reviewDto);

    //리뷰 수정
    public void reviewModify(ReviewDto reviewDto);

    //리뷰 목록
    public List<ReviewDto> reviewList(long id);

    public List<StoreDto> storeList(Map<String, Object> map);

    //찜하기
    public void addLikes(Map<String, Long> map);

    //찜하기 해제
    public void deleteLikes(Map<String, Long> map);

    //찜한 가게 목록
    public List<StoreDto> likesList(Map<String, Object> map);

    //매장 검색
    public List<StoreDto> storeSearch(Map<String, Object> map);
}
