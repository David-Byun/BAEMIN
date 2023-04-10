package c.lone.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {
    //try - catch문을 통한 커스텀 예외처리
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<String> apiException(CustomApiException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //try catch문 없이 전역 예외처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> globalException(Exception e) {
        return ResponseEntity.badRequest().body("예상치 못한 오류가 발생하였습니다. 관리자에게 문의하세요");
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> sqlException(SQLException e) {
        return ResponseEntity.badRequest().body("데이터베이스와 통신에 실패하였습니다");
    }

    /*
        @Transactional 적용시 checked 예외는 롤백되지 않지만, unchecked 예외는 롤백됨
     */
    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<String> rollbackException(UnexpectedRollbackException e) {
        return ResponseEntity.badRequest().body("데이터 처리를 정상적으로 완료하지 못했습니다");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> sqlintException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.badRequest().body("무결성 제약 위반이 발생했습니다");
    }

}





















