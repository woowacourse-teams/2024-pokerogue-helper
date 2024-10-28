package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer {

    @Field("name")
    private String name;

    @Field("koName")
    private String koName;

    @Field("types")
    private List<Type> types;

    @Field("pokemonIds")
    private List<String> pokemonIds;
}
