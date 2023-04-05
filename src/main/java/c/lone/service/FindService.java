package c.lone.service;

import c.lone.dao.FindMapper;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindService {

    private final FindMapper findMapper;
    private final JavaMailSender mailSender;
}
