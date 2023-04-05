package c.lone.dao;

import c.lone.dto.PointDto;
import c.lone.dto.ReviewDto;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //포인트 내역
    public List<PointDto> myPoint(long id);

    //리뷰 내역
    public List<ReviewDto> myReviewList(long id);

    //리뷰 삭제
    public void deleteReview(Map<String, Object> map);

    //유저 정보 수정
    public void modifyInfo(Map<String, Object> map);
}
