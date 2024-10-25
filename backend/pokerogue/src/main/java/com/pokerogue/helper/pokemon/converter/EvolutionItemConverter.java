package com.pokerogue.helper.pokemon.converter;

import com.pokerogue.helper.pokemon.data.EvolutionItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class EvolutionItemConverter implements Converter<String, EvolutionItem> {

    @Override
    public EvolutionItem convert(String evolutionItemData) {
        return EvolutionItem.convertFrom(evolutionItemData);
    }
}
