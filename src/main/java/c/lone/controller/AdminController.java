package c.lone.controller;

import c.lone.aop.IsMyStore;
import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.StoreDetailDto;
import c.lone.dto.StoreDto;
import c.lone.service.AdminService;
import c.lone.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
    AOP를 통해서 자기 관리가 아닌 스토어정보로 이동시에는 화면이 노출되지 않지만, 예외처리를 따로 하지 않았기 때문에 사용자한테 예외발생 내역이 그대로 보이게 됨
    이러한 내역을 사용자에게 노출하는 건 좋지 않기 때문에 AOP를 이용하여 한 곳에서 모든 예외에 대한 처리를 할 것이다.
 */
@Controller
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final StoreService storeService;

    @GetMapping("/admin/myStore")
    public String myStore(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        int userId = principal.getId();
        List<StoreDto> storeList = adminService.myStore(userId);
        model.addAttribute("storeList", storeList);
        return "admin/myStore";
    }

    @IsMyStore
    @GetMapping("/admin/management/{id}/detail")
    public String detail(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails principal, Model model) {
        int userId = principal.getId();
        StoreDetailDto storeDetailDto = storeService.storeDetail(id, userId);
        model.addAttribute("store", storeDetailDto);
        model.addAttribute("adminPage", true);
        return "admin/detail";
    }

    /*
        주문접수페이지
        주문 상태를 4가지로 나눔
        - 고객이 맨처음 주문을 완료하면 접수대기상태가 되며 사장님이 해당 주문을 수락하거나 취소할 수 있음
        - 수락시 처리중으로 넘어가며 처리중 탭에서 완료 체크시 배달 완료로 넘어감
        - 접수대기/처리중/주문취소/배달완료탭을 클릭시 해당 탭의 상태값(키워드)이 서버로 넘어가며 그 키워드로 order테이블에서 delivery_status칼럼 조회
     */
    @IsMyStore
    @GetMapping("/admin/management/order/{id}")
    public String order(@PathVariable long id) {
        return "admin/order";
    }

    @IsMyStore
    @GetMapping("/admin/management/sales/{id}")
    public String sales(@PathVariable long id) {
        return "admin/sales";
    }
}
