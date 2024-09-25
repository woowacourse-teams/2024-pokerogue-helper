package com.pokerogue.helper.pokemon.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


@Configuration
public class MongoConfiguration {
    @Bean
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory mongoDbFactory,
                                                MongoMappingContext mongoMappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setMapKeyDotReplacement("#DOT#");
        mongoConverter.setCustomConversions(customConversions());
        return mongoConverter;
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return MongoCustomConversions.create(adapter -> {
            adapter.registerConverter(new ReadTypeConverter());
            adapter.registerConverter(new ReadItemConverter());
        });

    }

}
