package c.lone.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class CustomUserDetails implements UserDetails {

    /*
        직렬화와 관련이 있는데 직렬화는 단기간 또는 장기간 데이터를 보존하는 용도
        직렬화를 해제하기 위해서는 직렬화 시점의 클래스와 해제 시점의 클래스가 일치해야 함
        저장기간이 길어지면서 클래스의 내용이 바뀌게 되면 직렬화 시점의 클래스와 해제 시점의 클래스가 일치하지 않아 직렬화 해제 불가
        serialVersionUID 선언해두면 클래스의 내용이 바뀌더라도 serialVersionUID의 변수의 값이 같으면 동일한 클래스로 간주하여 해제 가능

        사용자가 입력한 아이디가 DB에 있는지 확인해서 존재할 경우 그 회원정보를 방금 추가한 CustomUserDetails에 담아 가져옴
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private int point;
    private String role;

    // 계정이 가지고 있는 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드가 만료되었는지를 리턴
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 사용가능한지를 리턴
    @Override
    public boolean isEnabled() {
        return true;
    }
}
