package com.pokerogue.helper.biome.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
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

    public List<String> getTrainerTypes() {
        Set<String> trainerTypes = new HashSet<>();
        trainers.forEach(trainer -> trainerTypes.addAll(trainer.getTypes()));

        return trainerTypes.stream()
                .toList();
    }
}
