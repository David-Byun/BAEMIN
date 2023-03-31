package c.lone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
                .failureUrl("/auth/failed");

        return http.build();
    }
}
