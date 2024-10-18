package com.pokerogue.helper.global.config;

import com.pokerogue.helper.biome.converter.TierConverter;
import com.pokerogue.helper.move.converter.FlagConverter;
import com.pokerogue.helper.move.converter.MoveCategoryConverter;
import com.pokerogue.helper.move.converter.MoveTargetConverter;
import com.pokerogue.helper.pokemon.converter.EvolutionItemConverter;
import com.pokerogue.helper.type.converter.TypeReadConverter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.pokerogue"})
public class DataMongoDbConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new TypeReadConverter(),
                new EvolutionItemConverter(),
                new MoveCategoryConverter(),
                new MoveTargetConverter(),
                new FlagConverter(),
                new TierConverter()
        ));
    }
}
