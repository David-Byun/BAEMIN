package c.lone.aop;

import c.lone.service.AdminService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@AllArgsConstructor
@Component
public class AdminAOP {

    private final AdminService adminService;

    @Around("@annotation(l.one.")
}
