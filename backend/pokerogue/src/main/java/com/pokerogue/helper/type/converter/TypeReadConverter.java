package com.pokerogue.helper.type.converter;

import com.pokerogue.helper.type.data.Type;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TypeReadConverter implements Converter<String, Type> {

    @Override
    public Type convert(String typeData) {
        return Type.convertFrom(typeData);
    }
}
