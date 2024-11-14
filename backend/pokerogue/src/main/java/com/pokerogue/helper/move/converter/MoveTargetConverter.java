package com.pokerogue.helper.move.converter;

import com.pokerogue.helper.move.data.MoveTarget;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MoveTargetConverter implements Converter<String, MoveTarget> {

    @Override
    public MoveTarget convert(String moveTargetData) {
        return MoveTarget.convertFrom(moveTargetData);
    }
}
