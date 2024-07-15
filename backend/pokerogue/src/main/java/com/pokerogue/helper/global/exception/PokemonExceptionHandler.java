package com.pokerogue.helper.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class PokemonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<String> handleViolationException(GlobalCustomException e) {
        log.error("error message : {}", e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }
}
