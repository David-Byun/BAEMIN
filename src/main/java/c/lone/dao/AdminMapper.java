package c.lone.dao;

import org.apache.ibatis.annotations.Mapper;

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
}
