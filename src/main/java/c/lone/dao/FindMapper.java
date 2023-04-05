package c.lone.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FindMapper {

    //패스워드 찾기 이메일 일치 확인
    public String emailCheck(Map<String, Object> map);

    //패스워드 찾기 폰번호 일치 확인
    public String phoneCheck(Map<String, Object> map);

    //유저 ID 찾기
    public List<String> findId(String email);
}
