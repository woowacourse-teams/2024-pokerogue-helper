package com.pokerogue.helper.pokemon2.service;


import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.dto.BiomeResponse;
import com.pokerogue.helper.pokemon2.dto.EggMoveResponse;
import com.pokerogue.helper.pokemon2.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon2.dto.Pokemon2Response;
import com.pokerogue.helper.pokemon2.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.dto.Pokemon2DetailResponse;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Pokemon2Service {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;

    private List<Pokemon2Response> findAllCache = List.of();
    private Map<String, Pokemon2DetailResponse> findByIdCache = new HashMap<>();

    public List<Pokemon2Response> findAll() {
        if (findAllCache.isEmpty()) {
            initFindAllCache();
        }
        return findAllCache;
    }

    private void initFindAllCache() {
        findAllCache = pokemon2Repository.findAll().values().stream()
                .map(Pokemon2Response::from)
                .toList();
    }

    public Pokemon2DetailResponse findById(String id) {
        if (findByIdCache.isEmpty()) {
            initFindByIdCache();
        }
        return findByIdCache.get(id);
    }

    private void initFindByIdCache() {
        List<Pokemon2DetailResponse> pokemon2DetailResponses = pokemon2Repository.findAll().values()
                .stream()
                .map(this::toPokemon2DetailResponse)
                .toList();

        findByIdCache = pokemon2DetailResponses.stream().collect(
                Collectors.toMap(
                        Pokemon2DetailResponse::id,
                        Function.identity()
                ));
    }

    private Pokemon2DetailResponse toPokemon2DetailResponse(Pokemon pokemon) {
        List<PokemonTypeResponse> pokemonTypeResponses = List.of(
                PokemonTypeResponse.from(pokemon.type1()),
                PokemonTypeResponse.from(pokemon.type2())
        );
        List<PokemonAbilityResponse> pokemonAbilityResponses = List.of(
                PokemonAbilityResponse.from(pokemon.abilityPassive()),
                PokemonAbilityResponse.from(pokemon.abilityHidden()),
                PokemonAbilityResponse.from(pokemon.ability1()),
                PokemonAbilityResponse.from(pokemon.ability2())
        );
        //TODO: evol
        EvolutionResponse evolutionResponse = null;
        List<EggMoveResponse> moveResponse = createMoveResponse(pokemon.moves());
        List<EggMoveResponse> eggMoveResponse = createMoveResponse(pokemon.eggMoves());
        List<BiomeResponse> biomeResponse = createBiomeResponse(pokemon.biomes());
        return new Pokemon2DetailResponse(
                pokemon.id(),
                0L,
                pokemon.koName(),
                "image",
                pokemonTypeResponses,
                pokemonAbilityResponses,
                0,
                // pokemon.baseTotal(),
                0,
                0,
                0,
                0,
                0,
                0,
                false,
                false,
                false,
                // pokemon.legendary(),
                // pokemon.subLegendary(),
                // pokemon.mythical(),
                false,
//                pokemon.weight(),
//                pokemon.height(),
                0.0,
                0.0,
                evolutionResponse,
                moveResponse,
                eggMoveResponse,
                biomeResponse
        );
    }

    private List<BiomeResponse> createBiomeResponse(List<String> biomes) {
        return null;
    }

    private List<EggMoveResponse> createMoveResponse(List<String> strings) {
        return null;
    }
}
