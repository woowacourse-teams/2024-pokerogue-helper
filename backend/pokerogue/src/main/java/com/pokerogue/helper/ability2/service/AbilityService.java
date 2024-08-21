package com.pokerogue.helper.ability2.service;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability.dto.PokemonAbilityWithPokemonsResponse;
import com.pokerogue.helper.ability2.data.Ability;
import com.pokerogue.helper.ability2.data.AbilityPokemon;
import com.pokerogue.helper.ability2.dto.AbilityDetailResponse;
import com.pokerogue.helper.ability2.dto.AbilityPokemonResponse;
import com.pokerogue.helper.ability2.dto.AbilityTypeResponse;
import com.pokerogue.helper.ability2.repository.AbilityRepository;
import com.pokerogue.helper.ability2.dto.AbilityResponse2;
import com.pokerogue.helper.biome.data.BiomePokemonType;
import com.pokerogue.helper.biome.repository.BiomePokemonTypeImageRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbilityService {

    private final S3Service s3Service;
    private final AbilityRepository abilityRepository;
    private final BiomePokemonTypeImageRepository biomePokemonTypeImageRepository;

    public List<AbilityResponse2> findAbilities() {
        List<AbilityResponse2> list = abilityRepository.findAll().stream()
                .map(AbilityResponse2::from)
                .toList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        return abilityRepository.findAll().stream()
                .map(AbilityResponse2::from)
                .toList();
    }

    public AbilityDetailResponse findAbilityDetails(String id) {
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        List<AbilityPokemonResponse> abilityPokemonResponses = ability.getPokemons().stream()
                .map(abilityPokemon -> new AbilityPokemonResponse(
                        abilityPokemon.getId(),
                        abilityPokemon.getPokedexNumber(),
                        abilityPokemon.getName(),
                        s3Service.getPokemonImageFromS3(abilityPokemon.getId()),
                        getAbilityTypeResponses(abilityPokemon.getType1(), abilityPokemon.getType2())
                ))
                .toList();

        return AbilityDetailResponse.of(ability, abilityPokemonResponses);
    }

    private List<AbilityTypeResponse> getAbilityTypeResponses(String type1, String type2) {
        List<AbilityTypeResponse> abilityTypeResponses = new ArrayList<>();
        if (!type1.equals("Type.undefined")) {
            abilityTypeResponses.add(new AbilityTypeResponse(
                    biomePokemonTypeImageRepository.findPokemonTypeImageUrl(
                            BiomePokemonType.getBiomePokemonTypeByName(type1).name()), type1));
        }
        if (!type2.equals("Type.undefined")) {
            abilityTypeResponses.add(new AbilityTypeResponse(
                    biomePokemonTypeImageRepository.findPokemonTypeImageUrl(
                            BiomePokemonType.getBiomePokemonTypeByName(type2).name()), type2));
        }

        return abilityTypeResponses;
    }
}
