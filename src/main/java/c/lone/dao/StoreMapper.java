package c.lone.dao;

import c.lone.dto.FoodDto;
import c.lone.dto.FoodOptionDto;
import c.lone.dto.ReviewDto;
import c.lone.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    public List<StoreDto> storeList(int category, int address);

    public StoreDto storeDetail(long storeId);

    public List<FoodDto> foodList(long storeId);

    public List<FoodOptionDto> foodOption(long foodId);

    //리뷰 작성
    public void reviewWrite(ReviewDto reviewDto);

    //리뷰 수정
    public void reviewModify(ReviewDto reviewDto);
}
