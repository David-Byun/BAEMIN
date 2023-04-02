package c.lone.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 인증이 성공했을 때 처리하는 클래스
@Component
public class LoginSuccess implements AuthenticationSuccessHandler {

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String referer = (String) session.getAttribute("referer");

        if (referer != null) {
            String domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String uri = referer.replace(domain, "");
            //주문페이지에서 로그인 했다면 세션에 저장된 정보가 존재하므로 주문페이지로 이동
            if (uri.equals("/order")) {
                response.sendRedirect(uri);
                return;
            }
        }
        //존재하지 않는다면 home 페이지로 이동
        response.sendRedirect("/home");

    }
}
