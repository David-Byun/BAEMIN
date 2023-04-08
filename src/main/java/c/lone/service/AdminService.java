package c.lone.service;

import c.lone.dao.AdminMapper;
import c.lone.dto.FoodDto;
import c.lone.dto.OrderListDto;
import c.lone.dto.StoreDto;
import c.lone.utils.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    public List<StoreDto> myStore(long userId) {
        return adminMapper.myStore(userId);
    }

    //메뉴 추가
    @Transactional
    public void addMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice) {
        adminMapper.addMenu(foodDto);

        if (foodOption != null) {
            List<Map<String, Object>> optionList = new ArrayList<>();
            for (int i = 0; i < foodOption.length; i++) {
                Map<String, Object> optionMap = new HashMap<>();
                optionMap.put("optionName", foodOption[i]);
                optionMap.put("optionPrice", foodOptionPrice[i]);
                optionMap.put("foodId", foodDto.getId());
                optionList.add(optionMap);
            }
            adminMapper.addMenuOption(optionList);
        }
    }

    //메뉴수정
    @Transactional
    public void updateMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId) {
        Map<String, Object> map = new HashMap<>();
        if (foodOption == null) {
            adminMapper.deleteMenuOption(foodDto.getId());
        } else {
            List<Map<String, Object>> optionList = new ArrayList<>();
            for (int i = 0; i < foodOption.length; i++) {
                Map<String, Object> optionMap = new HashMap<>();
                long oid = -1;
                if (optionId.length != 0 && optionId[i] != null) {
                    oid = optionId[i];
                }

                //optionId가 없으면 -1 있으면 해당 optionId를 가져와서 같이 담는다.
                optionMap.put("optionId", oid);
                optionMap.put("optionName", foodOption[i]);
                optionMap.put("optionPrice", foodOptionPrice[i]);

                optionList.add(optionMap);
            }
            map.put("optionList", optionList);
        }
        map.put("food", foodDto);
        adminMapper.updateMenu(map);
    }

    //메뉴삭제
    public void deleteMenu(long storeId, long[] deleteNumber) {
        adminMapper.deleteMenu(storeId, deleteNumber);

    }

    //매장 정보 수정
    public void storeInfoUpdate(StoreDto storeDto) {
        adminMapper.storeInfoUpdate(storeDto);
    }

    //리뷰 답글
    public String bossComment(long storeId, String orderNum, String bossComment) {

        //사용자가 입력한 텍스트를 replace 메서드를 통해 바꿔주는 이유는 DB에 저장하고 불러올때 줄바꿈과 띄어쓰기를 유지하기 위함
        bossComment = bossComment.replace("\n", "<br>").replace(" ", "&nbsp");

        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("bossComment", bossComment);
        map.put("orderNum", orderNum);

        adminMapper.bossComment(map);
        return bossComment;
    }

    //주문 목록
    public List<OrderListDto> orderList(long storeId, String list, int page) {
        Page p = new Page(page, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("list", list);
        map.put("firstList", p.getFirstList());
        return adminMapper.orderList(map);
    }
}























