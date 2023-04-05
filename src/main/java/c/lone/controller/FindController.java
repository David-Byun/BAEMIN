package c.lone.controller;

import c.lone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/*
    생성자가 한개면 @Autowired 생략할 수 있다.
    Find : 비밀번호 및 아이디 찾기
    아이디 찾기 : 이메일 입력 > 일치하는 이메일이 존재하는지 확인 > 아이디 이메일 전송 > 아이디 확인
    비밀번호 찾기 : 아이디 입력 > 일치하는 아이디가 존재하는지 확인 > 이메일 또는 핸드폰 입력 > 해당 아이디 정보와 이메일 또는 핸드폰번호 일치 확인 > 이메일 또는 핸드폰번호로 인증번호 발송 > 인증번호 확인 > 비밀번호 변경
 */
@AllArgsConstructor
@Controller
public class FindController {
    private final UserService userService;

    //아이디 찾기 페이지
    @GetMapping("/find/id")
    public String findId() {
        return "user/find/findId";
    }

    //비밀번호 찾기 페이지
    @GetMapping("/find/password")
    public String findPassword() {
        return "user/find/findPassword";
    }

    @GetMapping("/find/modify/password")
    public String modifyPassword(String username, HttpSession session) {
        Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
        if (authStatus == null || !username.equals(authStatus.get("username"))) {
            return "redirect://find/password";
        }
        //페이지 왔을때 인증이 안되어 있다면
        if (!(boolean) authStatus.get("status")) {
            return "redirect:/find/password";
        }
        return "user/find/modify";
    }

}


























