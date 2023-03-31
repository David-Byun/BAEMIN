package c.lone.dao;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.SignupDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    public int usernameChk(String username);
    public void signup(SignupDto signupDto);

    public CustomUserDetails getUser(String username);
}
