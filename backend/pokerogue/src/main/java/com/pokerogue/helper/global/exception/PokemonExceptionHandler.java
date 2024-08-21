package com.pokerogue.helper.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class PokemonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<String> handleViolationException(GlobalCustomException e) {
        log.error("error message : {}", e.getStackTrace()[0]);

        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        log.error("error message : {}", e.getStackTrace()[0]);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("데이터 접근 에러가 발생했습니다.");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        log.error("error message : {}", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Null Pointer 에러가 발생했습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnExpectedException(Exception e) {
        log.error("error message : {}", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("핸들링 하지 않은 에러가 발생했습니다.");
    }

    @ExceptionHandler(OutOfMemoryError.class)
    public ResponseEntity<String> handleOutOfMemoryError(OutOfMemoryError e) {
        log.error("error message : {}", e.getStackTrace()[0]);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Out Of Memory 에러가 발생했습니다.");
    }
}
