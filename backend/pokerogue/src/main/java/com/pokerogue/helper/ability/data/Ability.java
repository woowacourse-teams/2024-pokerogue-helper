package com.pokerogue.helper.ability.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "ability")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<String> pokemonIds;

    @Field("isBypassFaint")
    private Boolean isBypassFaint;

    @Field("isIgnorable")
    private Boolean isIgnorable;

    public boolean isPresent() {
        return !id.equals("none");
    }
}
