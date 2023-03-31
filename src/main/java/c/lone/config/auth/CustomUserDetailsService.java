package c.lone.config.auth;

import c.lone.dao.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    AuthMapper authMapper;

    @Autowired
    public CustomUserDetailsService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    /*
        사용자로부터 입력받은 아이디를 통해 DB에 해당 아이디가 존재하는지 여부 확인하여 CustomUserDetails 객체에 해당 정보를 담아옴
        만약 해당 아이디를 가진 데이터가 테이블에 존재한다면 이 객체를 반환하고 이 객체는 유저의 password가 존재하므로 시큐리티가 사용자로부터
        받은 패스워드를 BCryptPasswordEncoder encode()를 통해 해시화하여 두 정보 비교 후 로그인 처리
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails principal = authMapper.getUser(username);
        if(principal == null) throw new UsernameNotFoundException("회원이 존재하지 않습니다");
        return principal;
    }
}
