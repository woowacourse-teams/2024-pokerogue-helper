package com.pokerogue.helper.move.converter;

import com.pokerogue.helper.move.data.MoveFlag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class FlagConverter implements Converter<String, MoveFlag> {

    @Override
    public MoveFlag convert(String flagData) {
        return MoveFlag.convertFrom(flagData);
    }
}
