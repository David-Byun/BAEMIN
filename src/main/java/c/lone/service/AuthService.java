package c.lone.service;

import c.lone.dao.AuthMapper;
import c.lone.dto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    AuthMapper authMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(AuthMapper authMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authMapper = authMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    /*
        bCryptPasswordEncoder를 통해 사용자로부터 입력받은 패스워드를 해시화한 다음 Dto의 setter를 통해 기존 password
        위 덮어씌운후 DB에 저장
     */

    @Transactional
    public void signup(SignupDto signupDto) {
        String encPassword = bCryptPasswordEncoder.encode(signupDto.getPassword());
        signupDto.setPassword(encPassword);
        authMapper.signup(signupDto);
    }
}
