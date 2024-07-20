package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.external.dto.Name;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.ability.FlavorTextEntry;
import com.pokerogue.helper.external.dto.pokemon.AbilitySummary;
import com.pokerogue.helper.external.dto.pokemon.PokemonDetails;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.external.dto.pokemon.Stat;
import com.pokerogue.helper.external.dto.pokemon.TypeSummary;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.type.domain.PokemonType;
import java.util.ArrayList;
import java.util.List;
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

    public PokemonDetails getPokemonDetails(PokemonSaveResponse pokemonSaveResponse) {
        int height = pokemonSaveResponse.height();
        int weight = pokemonSaveResponse.weight();
        NameAndUrl species = pokemonSaveResponse.species();
        List<Stat> stats = pokemonSaveResponse.stats();
        int hp = 0, defense = 0, attack = 0, speed = 0, specialAttack = 0, specialDefense = 0;
        for (Stat stat : stats) {
            String statName = stat.stat().name();
            if (statName.equals("hp")) {
                hp = stat.base_stat();
            }
            if (statName.equals("attack")) {
                attack = stat.base_stat();
            }
            if (statName.equals("defense")) {
                defense = stat.base_stat();
            }
            if (statName.equals("speed")) {
                speed = stat.base_stat();
            }
            if (statName.equals("special-attack")) {
                specialAttack = stat.base_stat();
            }
            if (statName.equals("special-defense")) {
                specialDefense = stat.base_stat();
            }
        }
        int totalStats = hp + defense + attack + speed + specialAttack + specialDefense;
        List<AbilitySummary> abilitySummaries = pokemonSaveResponse.abilities();
        List<String> abilityNameList = new ArrayList<>();
        for (AbilitySummary abilitySummary : abilitySummaries) {
            abilityNameList.add(abilitySummary.ability().name());
        }
        List<TypeSummary> typeSummaries = pokemonSaveResponse.types();
        List<String> typeNameList = new ArrayList<>();
        for (TypeSummary typeSummary : typeSummaries) {
            typeNameList.add(typeSummary.type().name());
        }
        return new PokemonDetails(pokemonSaveResponse.name(), weight, height, species, hp, attack, defense, speed, specialAttack, specialDefense, totalStats, abilityNameList, typeNameList);
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
