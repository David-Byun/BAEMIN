package c.lone.controller;

import c.lone.dto.SignupDto;
import c.lone.service.AuthService;
import c.lone.utils.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class AuthController {
    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/signin")
    public String signin() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signup() {
        return "auth/signup";
    }

    //로그인 실패시 에러메시지를 보여주기 위함
    @GetMapping("/auth/failed")
    public String failedSignin(Model model) {
        return Script.locationMsg("/auth/signin", "아이디 또는 비밀번호를 잘못 입력하셨습니다", model);
    }

    //Errors는 반드시 Request객체 바로 뒤에 위치해야함
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, Errors errors, Model model) {
        //유효성 검사 실패
        if (errors.hasErrors()) {
            //사용자로부터 입력받은 데이터를 유지하기 위함
            model.addAttribute("signupDto", signupDto);

            //유효성 검사에 실패한 필드와 메시지를 저장
            Map<String, String> validResult = authService.validHandling(errors);

            //필드를 key값으로 에러메시지 저장
            for (String key : validResult.keySet()) {
                System.out.println(key + validResult.get(key));
                model.addAttribute(key, validResult.get(key));
            }

            //사용자로부터 입력받은 데이터와 에러메시지를 가지고 회원가입 페이지로 이동
            return "auth/signup";
        }
    }


}
