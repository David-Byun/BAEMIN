package c.lone.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignupDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 4~12자의 영문 대소문자와 숫자로만 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "비밀번호는 4~12자의 영문 대소문자와 숫자로만 입력해주세요.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$", message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "별명은 2~5자의 한글로만 입력해주세요.")
    private String nickname;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰번호를 확인해 주세요")
    private String phone;
}
