package c.lone.dao;

import c.lone.dto.FoodDto;
import c.lone.dto.OrderListDto;
import c.lone.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
    포인트 처리하기 위해서는 Userid, storename, point가 필요하므로
    주문처리에서와 마찬가지로 Map을 생성하여 넣어주도록 함

 */
@Mapper
public interface AdminMapper {

    //포인트 테이블 내역 insert
    public int pointUpdate(Map<String, Object> pointUpdateMap);

    //유저 테이블 point update
    public int pointUpdateUser(Map<String, Object> pointUpdateMap);

    //운영중인 매장 목록
    public List<StoreDto> myStore(long userId);

    public void addMenu(FoodDto foodDto);

    public void deleteMenu(long storeId, long[] deleteNumber);

    public void updateMenu(Map<String, Object> foodUpdateMap);

    public void addMenuOption(List<Map<String, Object>> optionList);

    public void deleteMenuOption(long foodId);

    //매장 정보 수정
    public void storeInfoUpdate(StoreDto storeDto);

    //사장님 답글
    public void bossComment(Map<String, Object> map);

    //주문목록
    public List<OrderListDto> orderList(Map<String, Object> map);
    
}























