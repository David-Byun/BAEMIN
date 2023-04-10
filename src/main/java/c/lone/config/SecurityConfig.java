package c.lone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //스프링 시큐리티 활용하기 위한 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csfr 사용 안함, JWT방식을 제대로 쓰려고 하면 Restfull한 API 형태
        http.csrf().disable();

        String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"https://localhost:8080/report\"}]}";


        //URL 인증여부 설정
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .defaultSuccessUrl("/")
                .failureUrl("/auth/failed")
                .and()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO))
                .xssProtection()
                .and()
                .contentSecurityPolicy("form-action 'self'; report-uri /report; report-to csp-violation-report");
                /*
                    기존에는 로그인 성공시 자동으로 home페이지로 이동되도록 설정
                    로그인시에 바로 직전 페이지로 돌아가도록 해주기 위해 loginSucess라는 이름으로 successHandler를 하나 추가
                    defaultSuccessUrl("/") => successHandler(loginSuccess)
                */


        return http.build();
    }
}
