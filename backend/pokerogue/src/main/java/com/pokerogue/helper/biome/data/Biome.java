package com.pokerogue.helper.biome.data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "biome")
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

    private String image; // Todo: 지우기

    private Map<Tier, List<String>> pokemons; // Todo: 지우기

    private List<String> mainTypes; // Todo: 지우기

    public Biome(String id, String name, String image, Map<Tier, List<String>> pokemons, List<String> mainTypes,
                 List<Trainer> trainers, List<NextBiome> nextBiomes) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemons = pokemons;
        this.mainTypes = mainTypes;
        this.trainers = trainers;
        this.nextBiomes = nextBiomes;
    }

    public List<String> getTrainerTypes() {
        Set<String> trainerTypes = new HashSet<>();
        trainers.stream()
                .filter(trainer -> !trainer.getName().equals("없음"))
                .forEach(trainer -> trainerTypes.addAll(trainer.getTypes()));

        return trainerTypes.stream()
                .toList();
    }
}
