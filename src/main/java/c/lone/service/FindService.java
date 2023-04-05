package c.lone.service;

import c.lone.dao.FindMapper;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindService {

    private final FindMapper findMapper;
    private final JavaMailSender mailSender;

    public List<String> findId(String email) {
        return findMapper.findId(email);
    }

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
    }
}
