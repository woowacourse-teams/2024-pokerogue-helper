package com.pokerogue.helper.global.config;

import com.pokerogue.helper.biome.converter.SortingCriteriaRequestConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SortingCriteriaRequestConverter());
    }
}
