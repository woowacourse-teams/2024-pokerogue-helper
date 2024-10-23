package com.pokerogue.helper.global.constant;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum SortingCriteria {

    ASCENDING("asc"),
    DESCENDING("desc"),
    ;

    private final String value;

    SortingCriteria(String value) {
        this.value = value;
    }

    public static SortingCriteria convertFrom(String value) {
        return Arrays.stream(values())
                .filter(criteria -> criteria.value.equals(value))
                .findAny()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.INVALID_SORTING_CRITERIA));
    }
}
