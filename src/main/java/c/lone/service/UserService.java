package c.lone.service;

import c.lone.dao.UserMapper;
import c.lone.dto.PointDto;
import c.lone.dto.ReviewDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;

    public List<PointDto> myPoint(long id) {
        return userMapper.myPoint(id);
    }

    public List<ReviewDto> myReviewList(long id) {
        return userMapper.myReviewList(id);
    }

    public void deleteReview(long id, String orderNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", id);
        map.put("orderNum", orderNum);
        userMapper.deleteReview(map);
    }

    public void modifyInfo(String username, String valueType, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("valueType", valueType);
        map.put("value", value);
        userMapper.modifyInfo(map);
    }


}



























