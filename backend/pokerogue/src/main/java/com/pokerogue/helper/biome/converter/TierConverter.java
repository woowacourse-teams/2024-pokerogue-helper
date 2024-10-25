package com.pokerogue.helper.biome.converter;

import com.pokerogue.helper.biome.data.Tier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TierConverter implements Converter<String, Tier> {

    @Override
    public Tier convert(String tierData) {
        return Tier.convertFrom(tierData);
    }
}
