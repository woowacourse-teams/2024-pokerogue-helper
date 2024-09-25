package com.pokerogue.helper.pokemon.config;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.type.data.Type;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadTypeConverter implements Converter<String, Type> {

    @Override
    public Type convert(String name) {
        return Type.findByEngName(name)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
    }
}
