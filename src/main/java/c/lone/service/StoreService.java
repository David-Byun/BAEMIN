package c.lone.service;

import c.lone.dao.StoreMapper;
import c.lone.dto.FoodDto;
import c.lone.dto.FoodOptionDto;
import c.lone.dto.StoreDetailDto;
import c.lone.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {


    StoreMapper storeMapper;

    @Autowired
    public StoreService(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
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
