package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.external.dto.Name;
import com.pokerogue.helper.external.dto.InformationLink;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.ability.FlavorTextEntry;
import com.pokerogue.helper.external.dto.pokemon.PokemonDetail;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.external.dto.pokemon.StatDetail;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.type.domain.PokemonType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DtoParser {

    private static final String NOT_EXIST_KOREAN_NAME = "존재하지 않습니다";

    public PokemonAbility getPokemonAbility(AbilityResponse abilityResponse) {
        String koName = getKoName(abilityResponse.names());

        List<FlavorTextEntry> flavorTextEntries = abilityResponse.flavor_text_entries();
        String description = NOT_EXIST_KOREAN_NAME;
        description = getLatestVersionDescription(flavorTextEntries, description);

        return new PokemonAbility(abilityResponse.name(), koName, description, "자세한 설명입니다.");
    }

    private static String getLatestVersionDescription(List<FlavorTextEntry> flavorTextEntries, String description) {
        for (int i = flavorTextEntries.size() - 1; i > -1; i--) {
            if (flavorTextEntries.get(i).language().name().equals("ko")) {
                description = flavorTextEntries.get(i).flavor_text();
                break;
            }
        }

        return description;
    }

    private static String getKoName(List<Name> names) {
        return names.stream()
                .filter(Name::isKorean)
                .map(Name::name)
                .findFirst()
                .orElse(NOT_EXIST_KOREAN_NAME);
    }

    public PokemonType getPokemonType(TypeResponse typeResponse) {
        String koName = getKoName(typeResponse.names());

        return new PokemonType(typeResponse.name(), koName, "null");
    }

    public PokemonDetail getPokemonDetails(PokemonSaveResponse pokemonSaveResponse) {
        int height = pokemonSaveResponse.height();
        int weight = pokemonSaveResponse.weight();
        InformationLink species = pokemonSaveResponse.species();
        List<StatDetail> statDetails = pokemonSaveResponse.stats();
        Map<String, Integer> stat = getStat(statDetails);

        List<String> abilityNameList = pokemonSaveResponse.abilities().stream()
                .map(abilitySummary -> abilitySummary.ability().name())
                .toList();
        List<String> typeNameList = pokemonSaveResponse.types().stream()
                .map(typeSummary -> typeSummary.type().name())
                .toList();

        return new PokemonDetail(pokemonSaveResponse.name(), weight, height, species, stat.get("hp"),
                stat.get("attack"), stat.get("defense"), stat.get("speed"), stat.get("special-attack"),
                stat.get("special-defense"), stat.get("total-stats"), abilityNameList, typeNameList);
    }

    private Map<String, Integer> getStat(List<StatDetail> statDetails) {
        Map<String, Integer> stats = statDetails.stream()
                .collect(Collectors.toMap(
                        StatDetail::getStatName,
                        StatDetail::base_stat));

        int totalStats = stats.values().stream()
                        .mapToInt(Integer::intValue)
                                .sum();
        stats.put("total-stats", totalStats);

        return stats;
    }

    public PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        String koName = getKoName(pokemonSpeciesResponse.names());

        return new PokemonNameAndDexNumber(pokemonSpeciesResponse.id(), koName);
    }
}
