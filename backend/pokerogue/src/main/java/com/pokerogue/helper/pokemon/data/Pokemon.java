package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@RequiredArgsConstructor
@Document(collection = "pokemon")
public class Pokemon {

    @Id
    private final String id;

    @Field("imageId")
    private final String imageId;

    @Field("pokedexNumber")
    private final int pokedexNumber;

    @Field("name")
    private final String name;

    @Field("koName")
    private final String koName;

    @Field("speciesName")
    private final String speciesName;

    @Field("canChangeForm")
    private final boolean canChangeForm;

    @Field("formName")
    private final String formName;

    @Field("baseExp")
    private final int baseExp;

    @Field("friendship")
    private final int friendship;

    @Field("types")
    private final List<String> types; // Todo enum

    @Field("normalAbilityIds")
    private final List<String> normalAbilityIds;

    @Field("hiddenAbilityId")
    private final String hiddenAbilityId;

    @Field("passiveAbilityId")
    private final String passiveAbilityId;

    @Field("generation")
    private final int generation;

    @Field("legendary")
    private final boolean legendary;

    @Field("subLegendary")
    private final boolean subLegendary;

    @Field("mythical")
    private final boolean mythical;

    @Field("evolutions")
    private final List<Evolution> evolutions;

    @Field("formChanges")
    private final List<FormChange> formChanges;

    @Field("baseTotal")
    private final int baseTotal;

    @Field("hp")
    private final int hp;

    @Field("attack")
    private final int attack;

    @Field("defense")
    private final int defense;

    @Field("specialAttack")
    private final int specialAttack;

    @Field("specialDefense")
    private final int specialDefense;

    @Field("speed")
    private final int speed;

    @Field("height")
    private final double height;

    @Field("weight")
    private final double weight;

    @Field("eggMoveIds")
    private final List<String> eggMoveIds;

    @Field("levelMoves")
    private final List<LevelMove> levelMoves;

    @Field("technicalMachineMoveIds")
    private final List<String> technicalMachineMoveIds;

    @Field("biomeIds")
    private final List<String> biomeIds;

    public boolean hasSameType(Type type) {
        String name = type.getName();
        return this.types.stream()
                .anyMatch(name::equals);
    }
}
