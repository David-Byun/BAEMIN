package c.lone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


// @EnableAspectJAutoProxy : AOP는 프록시객체를 사용하기 때문에 스프링부트 어플리케이션 실행시 AOP 프록시객체를 사용하겠다고 선언
@EnableAspectJAutoProxy
@SpringBootApplication
public class LoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoneApplication.class, args);
	}

}
