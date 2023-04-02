package c.lone.utils;

import c.lone.config.auth.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;

/*
    스프링시큐리티를 사용하여 로그인을 하고 있기 때문에 주문이 완료되더라도
    재로그인을 하지 안으면 사용자는 포인트적립, 사용을 확인할 수 없음
    이유는 스프링 시큐리티는 로그인 요청시 회원테이블에서 해당 회원의 모든 정보를 가져와 세션에 저장해두고 있습니다.
    따라서 우리가 회원테이블에서 포인트를 변경하더라도 세션은 이미 그전에 회원정보를 저장하고 있기 때문에 테이블과 세션에 저장한 정보가 일치하지 않습니다.
    우리는 세션에 저장한 회원정보에 접근하여 포인트를 수정해줘야 합니다.
 */
public class UserInfoSessionUpdate {

    /*
        SecurityContextHolder 통한 인증객체 읽어오기
     */
    public static void sessionUpdate(String value, String valueType, CustomUserDetails principal, HttpSession session) {
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (valueType.equals("nickname")) {
            customUserDetail.setNickname(value);
        } else if (valueType.equals("password")) {
            customUserDetail.setPassword(value);
        } else if (value.equals("point")) {
            int point = customUserDetail.getPoint() + Integer.parseInt(value);
            customUserDetail.setPoint(point);
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(new UsernamePasswordAuthenticationToken(customUserDetail, null, principal.getAuthorities()));

        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

    }
}
