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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class AuthController {
    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /*
        비회원이 주문 이후 다시 초기 화면으로 가는 것을 막기 위해서 현재 페이지의 Header 정보를 세션에 저장해줌
        로그인 페이지로 이동하기 전에 현재 페이지의 Header정보를 세션에 저장해줌
     */
    @GetMapping("/auth/signin")
    public String signin(HttpServletRequest request, HttpSession session) {
        //웹브라우저 사용자인 클라이언로부터 서버로 요청이 들어오면 서버에서는 HttpServletRequest를 생성하며, 요청정보에 있는 매핑된 서블렛에게 전달

        //로그인, 로그아웃 했을 경우 이전페이지로 가지도록 로그인, 로그아웃 jsp에 request.getHeader("referer")를 만듦.
        String referer = request.getHeader("referer");
        session.setAttribute("referer", referer);
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
        //username이 이미 존재할 시 키값에 오류 메시지 저장
        if(authService.usernameChk(signupDto.getUsername()) != 0) {
            model.addAttribute("valid_username", "이미 등록된 아이디입니다.");
        }
        //유효성 검사 성공시 회원가입 서비스 로직 실행
        authService.signup(signupDto);

        //회원가입 성공 메시지 출력후 로그인페이지로 이동
        return Script.locationMsg("/auth/signin", "회원가입에 성공하였습니다.", model);
    }





}
