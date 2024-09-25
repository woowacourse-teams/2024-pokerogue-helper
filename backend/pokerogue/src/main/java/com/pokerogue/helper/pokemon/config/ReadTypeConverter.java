package com.pokerogue.helper.pokemon.config;

import com.pokerogue.helper.type.data.Type;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadTypeConverter implements Converter<String, Type> {

    @Override
    public Type convert(String name) {
        Optional<Type> optionalGender = Type.findByEngName(name);
        return optionalGender.orElseThrow();
    }
}
