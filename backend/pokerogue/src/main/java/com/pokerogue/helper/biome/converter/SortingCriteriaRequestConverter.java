package com.pokerogue.helper.biome.converter;

import com.pokerogue.helper.global.constant.SortingCriteria;
import org.springframework.core.convert.converter.Converter;

public class SortingCriteriaRequestConverter implements Converter<String, SortingCriteria> {

    @Override
    public SortingCriteria convert(String sortingCriteriaValue) {
        return SortingCriteria.convertFrom(sortingCriteriaValue);
    }
}
