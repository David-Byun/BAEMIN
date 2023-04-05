package c.lone.service;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dao.UserMapper;
import c.lone.dto.PointDto;
import c.lone.dto.ReviewDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/myPage")
    public String myPage() {
        return "user/myPage";
    }

    @GetMapping("/user/point")
    public String point(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        int id = principal.getId();
        List<PointDto> myPoint = userService.myPoint(id);
        model.addAttribute("myPoint", myPoint);
        model.addAttribute("point", principal.getPoint());
        return "user/point";
    }

    @GetMapping("/user/myReview")
    public String myReview(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        int id = principal.getId();
        List<ReviewDto> myReviewList = userService.myReviewList(id);
        model.addAttribute("myReviewList", myReviewList);
        return "user/myReview";
    }

    //회원 정보 수정 페이지
    @GetMapping("/user/myInfo")
    public String myInfo() {
        return "user/myInfo";
    }
}






















