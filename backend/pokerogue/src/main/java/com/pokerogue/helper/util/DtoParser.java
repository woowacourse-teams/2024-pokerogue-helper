package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.external.dto.Name;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.ability.FlavorTextEntry;
import com.pokerogue.helper.external.dto.pokemon.PokemonDetail;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.external.dto.pokemon.StatDetail;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.type.domain.PokemonType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DtoParser {

    public PokemonAbility getPokemonAbility(AbilityResponse abilityResponse) {
        List<Name> names = abilityResponse.names();
        String koName = "존재하지 않습니다";
        for (Name value : names) {
            if (value.language().name().equals("ko")) {
                koName = value.name();
            }
        }

        List<FlavorTextEntry> flavorTextEntries = abilityResponse.flavor_text_entries();
        String description = "존재하지 않습니다";
        for (int i = flavorTextEntries.size() - 1; i > -1; i--) {
            if (flavorTextEntries.get(i).language().name().equals("ko")) {
                description = flavorTextEntries.get(i).flavor_text();
            }
        }

        return new PokemonAbility(null, abilityResponse.name(), koName, description, "자세한 설명입니다.", new ArrayList<>());
    }

    public PokemonType getPokemonType(TypeResponse typeResponse) {
        String koName = "존재하지 않습니다";
        List<Name> names = typeResponse.names();
        for (Name value : names) {
            if (value.language().name().equals("ko")) {
                koName = value.name();
            }
        }

        return new PokemonType(null, typeResponse.name(), koName, "null");
    }

    public PokemonDetail getPokemonDetails(PokemonSaveResponse pokemonSaveResponse) {
        int height = pokemonSaveResponse.height();
        int weight = pokemonSaveResponse.weight();
        NameAndUrl species = pokemonSaveResponse.species();
        List<StatDetail> statDetails = pokemonSaveResponse.statDetails();
        Map<String, Integer> stat = getStat(statDetails);

        List<String> abilityNameList = pokemonSaveResponse.abilities().stream()
                .map(abilitySummary -> abilitySummary.ability().name())
                .toList();
        List<String> typeNameList = pokemonSaveResponse.types().stream()
                .map(typeSummary -> typeSummary.type().name())
                .toList();

        return new PokemonDetail(pokemonSaveResponse.name(), weight, height, species, stat.get("hp"), stat.get("attack"), stat.get("defense"), stat.get("speed"), stat.get("special-attack"), stat.get("special-defense"), stat.get("total-stats"), abilityNameList, typeNameList);
    }

    private Map<String, Integer> getStat(List<StatDetail> statDetails) {
        Map<String, Integer> res = new HashMap<>();
        for (StatDetail statDetail : statDetails) {
            res.put(statDetail.stat().name(), statDetail.base_stat());
        }

        int totalStats = 0;
        for (Integer baseStat : res.values()) {
            totalStats += baseStat;
        }
        res.put("total-stats", totalStats);

        return res;
    }

    public PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        String koName = "존재하지 않습니다";
        List<Name> names = pokemonSpeciesResponse.names();
        for (Name value : names) {
            if (value.language().name().equals("ko")) {
                koName = value.name();
            }
        }

        return new PokemonNameAndDexNumber(pokemonSpeciesResponse.id(), koName);
    }
}
