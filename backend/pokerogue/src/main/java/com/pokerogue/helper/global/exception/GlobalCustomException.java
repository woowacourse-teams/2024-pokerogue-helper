package com.pokerogue.helper.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class GlobalCustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GlobalCustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.httpStatus = errorMessage.getHttpStatus();
    }
}
