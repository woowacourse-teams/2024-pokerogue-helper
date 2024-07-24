package com.pokerogue.external.pokemon.parser;

import com.pokerogue.external.pokemon.dto.DataUrl;
import com.pokerogue.external.pokemon.dto.Name;
import com.pokerogue.external.pokemon.dto.ability.AbilityResponse;
import com.pokerogue.external.pokemon.dto.ability.FlavorTextEntry;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonDetail;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.external.pokemon.dto.pokemon.StatDetail;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.type.domain.PokemonType;
import java.util.Comparator;
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
        String description = getLatestVersionDescription(flavorTextEntries).replace("\n", " ");

        return new PokemonAbility(abilityResponse.name(), koName, description, "자세한 설명입니다.");
    }

    private static String getLatestVersionDescription(List<FlavorTextEntry> flavorTextEntries) {
        return flavorTextEntries.stream()
                .sorted(Comparator.comparingInt(flavorTextEntries::indexOf).reversed())
                .filter(entry -> entry.language().isKorean())
                .map(FlavorTextEntry::flavor_text)
                .findFirst()
                .orElse(NOT_EXIST_KOREAN_NAME);
    }

    public PokemonType getPokemonType(TypeResponse typeResponse, String typeImage) {
        String koName = getKoName(typeResponse.names());

        return new PokemonType(typeResponse.name(), koName, typeImage);
    }

    public PokemonDetail getPokemonDetails(PokemonSaveResponse pokemonSaveResponse) {
        double height = pokemonSaveResponse.height() * 1.0 / 10;
        double weight = pokemonSaveResponse.weight() * 1.0 / 10;
        DataUrl species = pokemonSaveResponse.species();
        List<StatDetail> statDetails = pokemonSaveResponse.stats();
        Map<String, Integer> stat = getStat(statDetails);

        return new PokemonDetail(pokemonSaveResponse.name(), weight, height, species, stat.get("hp"),
                stat.get("attack"), stat.get("defense"), stat.get("speed"), stat.get("special-attack"),
                stat.get("special-defense"), stat.get("total-stats"));
    }

    private Map<String, Integer> getStat(List<StatDetail> statDetails) {
        Map<String, Integer> stats = statDetails.stream()
                .collect(Collectors.toMap(StatDetail::getStatName, StatDetail::base_stat));

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

    private static String getKoName(List<Name> names) {
        return names.stream()
                .filter(Name::isKorean)
                .map(Name::name)
                .findFirst()
                .orElse(NOT_EXIST_KOREAN_NAME);
    }
}
