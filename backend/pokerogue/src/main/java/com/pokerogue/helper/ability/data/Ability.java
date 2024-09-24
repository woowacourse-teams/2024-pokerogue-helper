package com.pokerogue.helper.ability.data;

import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "ability")
public class Ability {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("koName")
    private String koName;

    @Field("released")
    private String released;

    @Field("description")
    private String description;

    @Field("generation")
    private int generation;

    @Field("pokemonIds")
    private List<Integer> pokemonIds;

    @Field("isBypassFaint")
    private Boolean isBypassFaint;

    @Field("isIgnorable")
    private Boolean isIgnorable;

    // Todo: 지우기
    private List<InMemoryPokemon> inMemoryPokemons;

    public Ability(String id, String name, String description, List<InMemoryPokemon> inMemoryPokemons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.inMemoryPokemons = inMemoryPokemons;
    }
}
