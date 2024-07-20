package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.pokemon.repository.PokemonAbilityMappingRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.pokemon.repository.PokemonTypeMappingRepository;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Saver {
    private PokemonRepository pokemonRepository;
    private PokemonAbilityRepository pokemonAbilityRepository;
    private PokemonTypeRepository pokemonTypeRepository;
    private PokemonAbilityMappingRepository pokemonAbilityMappingRepository;
    private PokemonTypeMappingRepository pokemonTypeMappingRepository;
    private DtoParser dtoParser;
    private PokeClient pokeClient;

    public PokemonAbility savePokemonAbility(AbilityResponse abilityResponse) {
        PokemonAbility pokemonAbility = dtoParser.getPokemonAbility(abilityResponse);
        return pokemonAbilityRepository.save(pokemonAbility);
    }

    public List<PokemonAbility> savePokemonAbilityList(List<AbilityResponse> abilityResponses) {
        List<PokemonAbility> pokemonAbilities = new ArrayList<>();
        for (AbilityResponse abilityResponse : abilityResponses) {
            PokemonAbility pokemonAbility = dtoParser.getPokemonAbility(abilityResponse);
            pokemonAbilities.add(pokemonAbility);
        }
        return pokemonAbilityRepository.saveAll(pokemonAbilities);
    }

    public PokemonType savePokemonType(TypeResponse typeResponse) {
        PokemonType pokemonType = dtoParser.getPokemonType(typeResponse);
        return pokemonTypeRepository.save(pokemonType);
    }

    public List<PokemonType> savePokemonTypeList(List<TypeResponse> typeResponses) {
        List<PokemonType> pokemonTypes = new ArrayList<>();
        for (TypeResponse typeResponse : typeResponses) {
            PokemonType pokemonType = dtoParser.getPokemonType(typeResponse);
            pokemonTypes.add(pokemonType);
        }
        return pokemonTypeRepository.saveAll(pokemonTypes);
    }
}
