package c.lone.service;

import c.lone.dao.FindMapper;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FindService {

    private final FindMapper findMapper;
    private final JavaMailSender mailSender;

    public List<String> findId(String email) {
        return findMapper.findId(email);
    }

    /*
       이메일주소로 DB 검색
       이메일은 유니크값이 아니므로 같은 이메일을 가진 여러 아이디가 존재할 수 있기 때문에 List로 데이터를 받아야함.
     */

    public void sendUsernames(String email, List<String> usernames) {

        //SimpleMailMessage는 이메일을 쉽게 작성가능하도록 도와주는 스프링프레임워크가 제공하는 도구
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        //받는 사람 주소
        simpleMailMessage.setTo(email);

        //제목
        simpleMailMessage.setSubject("아이디 찾기");

        //StringBuffer : 문자열 연산 class
        StringBuffer sb = new StringBuffer();
        sb.append("가입하신 아이디는");
        sb.append(System.lineSeparator());

        for (int i = 0; i < usernames.size() - 1; i++) {
            sb.append(usernames.get(i));
            sb.append(System.lineSeparator());
        }
        sb.append(usernames.get(usernames.size() - 1)).append("입니다");
        simpleMailMessage.setText(sb.toString());

        /*
            mailSender.send(simpleMailMessage)는 이메일을 발송하는 부분인데 몇초가량 시간이 걸리므로 그 시간 동안 다른 작업을 처리할 수 있도록 새로운 쓰레드로 감싸주도록 합니다.
         */
        new Thread(new Runnable() {
            public void run() {
                mailSender.send(simpleMailMessage);
            }
        }).start();



    }
    public boolean emailCheck(String username, String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        String result = findMapper.emailCheck(map);
        if ("1".equals(result)) {
            return true;
        }
        return false;
    }

    public boolean phoneCheck(String username, String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("phone", phone);
        String result = findMapper.phoneCheck(map);
        if ("1".equals(result)) {
            return true;
        }
        return false;
    }

    public void sendAuthNum(String email, String authNum) {
        /*
            메일서버(SMTP Server)를 이용한 메일 전송
            MimeMessage 객체 : 멀티파트 데이터도 처리할 수 있음
            SimpleMailMessage 객체 : 텍스트 데이터만 전송 가능
         */
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("비밀번호 찾기 인증번호");

        String text = "인증번호는 " + authNum + "입니다";
        simpleMailMessage.setText(text);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mailSender.send(simpleMailMessage);
            }
        }).start();
    }


}



























