package com.pokerogue.helper.pokemon.config;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.EvolutionItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class EvolutionItermConverter implements Converter<String, EvolutionItem> {

    @Override
    public EvolutionItem convert(String id) {
        return EvolutionItem.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.EVOLUTION_NOT_FOUND));
    }
}
