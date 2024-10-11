package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "pokemon")
public class Pokemon {

    @Id
    private String id;

    @Field("imageId")
    private String imageId;

    @Field("pokedexNumber")
    private int pokedexNumber;

    @Field("name")
    private String name;

    @Field("koName")
    private String koName;

    @Field("speciesName")
    private String speciesName;

    @Field("canChangeForm")
    private boolean canChangeForm;

    @Field("formName")
    private String formName;

    @Field("baseExp")
    private int baseExp;

    @Field("friendship")
    private int friendship;

    @Field("types")
    private List<Type> types;

    @Field("normalAbilityIds")
    private List<String> normalAbilityIds;

    @Field("hiddenAbilityId")
    private String hiddenAbilityId;

    @Field("passiveAbilityId")
    private String passiveAbilityId;

    @Field("generation")
    private int generation;

    @Field("legendary")
    private boolean legendary;

    @Field("subLegendary")
    private boolean subLegendary;

    @Field("mythical")
    private boolean mythical;

    @Field("evolutions")
    private List<Evolution> evolutions;

    @Field("formChanges")
    private List<FormChange> formChanges;

    @Field("baseTotal")
    private int baseTotal;

    @Field("hp")
    private int hp;

    @Field("attack")
    private int attack;

    @Field("defense")
    private int defense;

    @Field("specialAttack")
    private int specialAttack;

    @Field("specialDefense")
    private int specialDefense;

    @Field("speed")
    private int speed;

    @Field("height")
    private double height;

    @Field("weight")
    private double weight;

    @Field("eggMoveIds")
    private List<String> eggMoveIds;

    @Field("levelMoves")
    private List<LevelMove> levelMoves;

    @Field("technicalMachineMoveIds")
    private List<String> technicalMachineMoveIds;

    @Field("biomeIds")
    private List<String> biomeIds;
}
