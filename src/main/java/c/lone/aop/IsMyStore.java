package c.lone.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    스프링 시큐리티를 적용할 때 일반 사용자는 관리자(사장님) 페이지에 접근이 불가능하도록 설정
    관리자로 등록되어 있는 사용자라면 관리자메뉴를 통해 자신이 운영중인 매장페이지에 들어가 여러가지 기능을 수행할 수 있었음
    현재 관리자로 등록된 이용자라면 주소창에 직접 입력을 통해 자신의 매장이 아니더라도 접근이 가능함
    따라서 우리는 관리자라도 자신이 운영중인 매장이 아니라면 관리자 페이지에 접근할 수 없도록 막아야함

    가장 간단한 방법은 현재 로그인한 사용자가 접근하려고 하는 매장의 주인인지 아닌지를 테이블에서 조회하는 방법이 있으나,
    이 경우 매 접속시마다 테이블에 쿼리를 날려 조회하기 때문에 좋은 방법이 아님

    다른 방법으로는 테이블에서 현재 사용자가 운영중인 모든 매장번호를 조회하여 리스트로 세션에 저장하는 방법.
    맨 처음에만 테이블을 조회하므로 DB와의 반복적인 커넥션을 없앨 수 있지만, 보안이 필요한 모든 페이지에서 세션을 조회하는 코드가 중복되어 들어가므로
    불필요한 코드중복을 없애고 필요한 곳에서만 해당 기능을 사용할 수 있도록 AOP를 이용하여 어노테이션화를 시킴
 */
@Target(ElementType.METHOD) // Method declaration
@Retention(RetentionPolicy.RUNTIME)
/*
    @Retention(RetentionPolicy.RUNTIME)이 달린 어노테이션은 컴파일된 후 클래스 파일에 포함되어 런타임 시에도 어노테이션 정보를 유지할 수 있습니다. 이러한 유지된 어노테이션 정보는 자바 리플렉션(Reflection)을 사용하여 런타임 시에 프로그램에서 참조할 수 있습니다.
 */
public @interface IsMyStore {

}
