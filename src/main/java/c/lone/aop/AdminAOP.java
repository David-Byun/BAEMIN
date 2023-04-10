package c.lone.aop;

import c.lone.config.auth.CustomUserDetails;
import c.lone.dto.FoodDto;
import c.lone.dto.StoreDto;
import c.lone.service.AdminService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Aspect //공통 기능을 적용할 클래스 설정
@AllArgsConstructor
@Component
public class AdminAOP {

    private final AdminService adminService;

    /*
        @Around는 메소드 호출 이전, 이후, 예외발생 등 모든 시점에서 동작하도록 해주며 annotation에 이전에 만든 어노테이션의 위치를 적어 해당 어노테이션에서 메서드가 실행되도록 해줌
        (로그인 5분 후에 만료등 로직등 구현할때 이용)
        ProceedingJointPoint는 해당 어노테이션이 붙어 있는 타깃을 뜻하며 해당 타깃 메서드의 여러 정보를 가져올 수 있음
     */
    @Around("@annotation(c.lone.aop.IsMyStore)")
    public Object myStore(ProceedingJoinPoint j) throws Throwable {
        long storeId = 0;
        Object[] args = j.getArgs();
        if (args.length > 0) {
            Object arg = args[0];

            if (arg instanceof Long) {
                storeId = (long) arg;
            } else if (arg instanceof StoreDto) {
                storeId = ((StoreDto) arg).getId();
            } else if (arg instanceof FoodDto) {
                storeId = ((FoodDto) arg).getStoreId();
            }
        }
        if (!isMyStore(storeId)) {
            return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
        }
        Object returnObj = j.proceed();
        return returnObj;
    }

    /*
       RequestContextHoler : Spring 전역으로 Request에 대한 정보를 가져오고자 할 때 사용하는 유틸성 클래스. Cookie, Header..등의 정보를 얻을 수 있음
        주로 Controller가 아닌 Business Layer등에서 Request 객체를 참고하려 할 때 사용한다.
        매번 method의 call param으로 넘기기가 애매할때 주로 쓰인다. 주로 Wrapping해서 사용한다
     */
    /*
        세션을 참조하여 세션이 비어있을 경우 테이블에서 조회하여 운영중인 매장 리스트를 세션에 저장하고 이렇게 저장한 리스트에 접근하고자 하는 매장 ID가 존재하면 타깃 메서드를 실행한다.
    */
    public boolean isMyStore(long storeId) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        List<Long> storeIdList = (List<Long>) session.getAttribute("myStore");

        /*
            SecurityContext : Authentication 객체가 저장되는 보관소로 필요 시 언제든지 Authentication 객체를 꺼내어 쓸 수 있도록 제공되는 클래스. ThreadLocal에 저장되어 아무 곳에서나 참조가 가능하도록 설계함. 인증이 완료되면 HttpSession에 저장되어 어플리케이션 전반에 걸쳐 전역적인 참조가 가능하다.
         */
        if (storeIdList == null) {
            SecurityContext sc = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            CustomUserDetails user = (CustomUserDetails) sc.getPrincipal();
            /*
                블로그상에는 다음 코드이지만 getAuthentication()이 없어서 위와 같이 작성
                CustomUserDetails user = (CustomUserDetails) sc.getAuthentication().getPrincipal();
             */
            long userId = user.getId();
            storeIdList = adminService.getMyStoreId(userId);
            session.setAttribute("myStore", storeIdList);
        }
        if (storeIdList.size() == 0) {
            return false;
        } else {
            if (storeIdList.contains(storeId)) {
                return true;
            } else {
                return false;
            }
        }
    }
}

/*
    이 코드는 스프링 AOP(Aspect-Oriented Programming)에서 @IsMyStore 어노테이션이 달린 메서드에 적용되는 aspect입니다.

    메서드의 실행 전과 후에 추가적인 기능을 제공하며, 이 경우 메서드 실행 전에 매개변수로 받은 값을 이용하여 해당하는 가게의 정보를 확인하고, 현재 사용자가 그 가게의 주인인지를 확인합니다.

    만약 현재 사용자가 해당 가게의 주인이 아니라면, 401 Unauthorized 응답을 반환합니다. 그렇지 않은 경우, 원래의 메서드를 실행하고 반환값을 반환합니다.

    이 코드는 애플리케이션에서 가게와 관련된 작업에 대한 권한을 검사하고, 권한이 없는 경우 요청을 거부하는 보안 기능을 제공하는 데 사용될 수 있습니다.

    AOP aspect 메서드인 myStore에서 매개변수로 받은 객체 배열 args에서 첫 번째 인자를 추출하여 storeId 변수에 할당하는 부분입니다.

    우선 storeId 변수를 0으로 초기화합니다. 그 다음, j.getArgs()를 사용하여 AOP aspect를 적용하는 메서드의 인자 목록을 가져옵니다.

    인자 목록에 원소가 있는 경우, args[0]을 사용하여 첫 번째 인자를 추출합니다. 추출한 인자가 Long 타입인 경우, storeId 변수에 그 값을 그대로 할당합니다.

    만약 추출한 인자가 StoreDto 타입인 경우, ((StoreDto) arg).getId()을 사용하여 StoreDto 객체에서 id 필드를 추출하여 storeId 변수에 할당합니다.

    마지막으로, 추출한 인자가 FoodDto 타입인 경우, ((FoodDto) arg).getStoreId()을 사용하여 FoodDto 객체에서 storeId 필드를 추출하여 storeId 변수에 할당합니다.

    이렇게 추출한 storeId 변수는 이후의 코드에서 해당 가게의 정보를 검사하고 권한을 확인하는 데 사용됩니다.

     AOP aspect 메서드 myStore가 실행되고 난 뒤, 원래의 메서드를 실행하는 부분입니다.

    j.proceed()는 AOP aspect가 적용된 메서드의 원래 로직을 실행하는 메서드입니다. 이 부분에서는 j.proceed()를 호출하여 원래의 메서드를 실행하고, 그 결과를 Object 타입의 returnObj 변수에 할당합니다.

    그리고 나서 return returnObj;를 사용하여 myStore 메서드를 호출한 곳으로 원래의 메서드 실행 결과를 반환합니다. 이를 통해 AOP aspect가 적용된 메서드를 실행하면서 추가적인 로직을 수행하고, 그 결과를 그대로 반환할 수 있게 됩니다.
 */












