package com.pokerogue.helper.pokemon.config;

import com.pokerogue.helper.pokemon.data.EvolutionItem;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadItemConverter implements Converter<String, EvolutionItem> {

    @Override
    public EvolutionItem convert(String id) {
        Optional<EvolutionItem> item = EvolutionItem.findById(id);
        return item.orElseThrow();
    }
}
