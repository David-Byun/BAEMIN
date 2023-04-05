package c.lone.controller;

import c.lone.service.AuthService;
import c.lone.service.FindService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class FindApiController {

    private final FindService findService;
    private final BCryptPasswordEncoder encodePwd;
    private final AuthService authService;

    //메일로 아이디 보내기
    @PostMapping("/api/find/sendUsernames")
    public ResponseEntity<Object> sendEmail(String email) {
        List<String> usernames = findService.findId(email);
        if (usernames.size() != 0) {
            findService.sendUsernames(email, usernames);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }

    /*
        아이디가 존재하는지 확인
        아이디가 존재하면 1, 아니면 0이 리턴되고 존재할 경우 세션에 username(유저아이디)를 저장하는데 그 이유는 아이디 확인 이후 아이디가 존재할 경우에만 인증페이지로 넘어감
        /find/password/auth?username="admin"을 주소창에 입력하여 아이디확인을 하지 않고 바로 접근하는 경우를 막기 위해 세션에 아이디를 저장
     */
    @GetMapping("/api/find/overlapCheck")
    public ResponseEntity<String> overlapCheck(String username, HttpSession session) {
        if (authService.usernameChk(username) != 0) {
            Map<String, Object> authStatus = new HashMap<>();
            authStatus.put("username", username);
            authStatus.put("status", false);

            session.setMaxInactiveInterval(300);
            session.setAttribute("authStatus", authStatus);

            return ResponseEntity.ok().body(username);
        }
        return ResponseEntity.badRequest().body("아이디가 존재하지 않습니다");
    }

    //인증번호 보내기 페이지
    @GetMapping("/find/password/auth")
    public String auth(String username, HttpSession session) {

        Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
        if (authStatus == null || !username.equals(authStatus.get("username"))) {
            return "redirect:/find/password";
        }
        return "user/find/auth";
    }

    //username의 이메일이 맞는지 확인
    @GetMapping("/api/find/password/emailCheck")
    public ResponseEntity<Boolean> emailCheck(String username, String email) {
        boolean emailCheck = findService.emailCheck(username, email);
        return new ResponseEntity<Boolean>(emailCheck, HttpStatus.OK);
    }

    //username의 전화번호가 맞는지 확인
    @GetMapping("/api/find/password/phoneCheck")
    public ResponseEntity<Boolean> phoneCheck(String username, String phone) {
        boolean phoneCheck = findService.phoneCheck(username, phone);
        return new ResponseEntity<Boolean>(phoneCheck, HttpStatus.OK);
    }

    //인증 완료 후
    @GetMapping("/api/find/auth/comletion")
    public ResponseEntity<String> authCompletion(HttpSession session) {
        Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
        if (authStatus == null) {
            return new ResponseEntity<String>("인증 시간이 만료되었습니다", HttpStatus.BAD_REQUEST);
        }
         /*
            auth 인증 완료시 status에 true 입력하는 이유는 비밀번호 변경 페이지는 get요청이므로
            username을 주소에 포함하여 화면 요청시 인증단계 거치지 않고 주소입력 접근 막기 위함
         */
        authStatus.put("status", true);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
























