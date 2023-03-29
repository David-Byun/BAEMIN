package c.lone.service;

import c.lone.dao.AuthMapper;
import c.lone.dto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    AuthMapper authMapper;

    @Autowired
    public AuthService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Transactional
    public Map<String, String> validHandling(Errors errors){
        Map<String, String> validResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            validResult.put("valid_" + error.getField(), error.getDefaultMessage());
        }
        return validResult;
    }

    //아이디 중복 확인
    @Transactional
    public int usernameChk(String username) {
        return authMapper.usernameChk(username);
    }

    //회원가입
    public void signup(SignupDto signupDto) {
        authMapper.signup(signupDto);
    }

}
