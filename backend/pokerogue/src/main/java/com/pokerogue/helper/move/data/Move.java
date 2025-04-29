package com.pokerogue.helper.move.data;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "move")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Move {

    @Id
    private String id;

    @Field("index")
    private String index;

    @Field("name")
    private String name;

    @Field("type")
    private Type type;

    @Field("moveCategory")
    private MoveCategory moveCategory;

    @Field("moveTarget")
    private MoveTarget moveTarget;

    @Field("power")
    private int power;

    @Field("accuracy")
    private int accuracy;

    @Field("powerPoint")
    private int powerPoint;

    @Field("effect")
    private String effect;

    @Field("effectChance")
    private int effectChance;

    @Field("priority")
    private int priority;

    @Field("generation")
    private int generation;

    @Field("released")
    private String released;

    @Field("flags")
    private List<MoveFlag> flags;

    @Field("pokemonIds")
    private List<String> pokemonIds;

    @Field("language")
    private String language;

    public boolean hasSameLanguage(String language) {
        return this.language.equals(language);
    }

    public boolean isAttackMove() {
        return this.moveCategory != MoveCategory.STATUS;
    }
}
