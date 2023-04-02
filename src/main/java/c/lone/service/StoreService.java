package c.lone.service;

import c.lone.dao.StoreMapper;
import c.lone.dto.*;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreMapper storeMapper;

    @Autowired
    FileUpload fileUpload;

    /*
        리뷰를 작성할 때 사용자는 사진을 올릴 수도 있고 올리지 않을수도 있기 때문에

     */
    @Transactional
    public boolean reviewWrite(ReviewDto reviewDto) {
        if (reviewDto.getFile() == null) {
            reviewDto.setReviewImg("");
        } else {
            if (!fileUpload.uploadReviewImg(reviewDto)) {
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
        return storeMapper.storeList(category, address);
    }

    @Transactional
    public StoreDetailDto storeDetail(long storeId) {
        StoreDto storeDto = storeMapper.storeDetail(storeId);
        List<FoodDto> foodList = storeMapper.foodList(storeId);
        return new StoreDetailDto(storeDto, foodList);
    }

    @Transactional
    public List<FoodOptionDto> foodOption(long foodId) {
        return storeMapper.foodOption(foodId);
    }

}
