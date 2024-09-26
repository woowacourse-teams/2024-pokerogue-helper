package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "biome")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Biome {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("koName")
    private String koName;

    @Field("types")
    private List<String> types; // Todo: List<Type> 으로 바꾸기

    @Field("nextBiomes")
    private List<NextBiome> nextBiomes;

    @Field("nativePokemons")
    private List<NativePokemon> nativePokemons;

    @Field("trainers")
    private List<Trainer> trainers;
}
