package com.pokerogue.helper.move.converter;

import com.pokerogue.helper.battle.MoveCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MoveCategoryConverter implements Converter<String, MoveCategory> {

    @Override
    public MoveCategory convert(String moveCategoryDate) {
        return MoveCategory.convertFrom(moveCategoryDate);
    }
}
