package com.pokerogue.helper.pokemon2.service;


import com.pokerogue.helper.pokemon2.Ability;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.data.Type;
import com.pokerogue.helper.pokemon2.dto.BiomeResponse;
import com.pokerogue.helper.pokemon2.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon2.dto.MoveResponse;
import com.pokerogue.helper.pokemon2.dto.Pokemon2Response;
import com.pokerogue.helper.pokemon2.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.dto.Pokemon2DetailResponse;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
                .sorted(Comparator.comparingLong(Pokemon2Response::pokedexNumber))
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
        List<PokemonTypeResponse> pokemonTypeResponses = createTypeResponse(pokemon);
        List<PokemonAbilityResponse> pokemonAbilityResponses = createAbilityResponse(pokemon);
        //TODO: evol
        EvolutionResponse evolutionResponse = null;
        List<MoveResponse> moveResponse = createMoveResponse(pokemon.moves());
        List<MoveResponse> eggMoveResponse = createEggMoveResponse(pokemon.eggMoves());
        List<BiomeResponse> biomeResponse = createBiomeResponse(pokemon.biomes());
        List<Integer> stats = Arrays.stream(pokemon.baseStats().split(","))
                .map(Integer::parseInt)
                .toList();

        return new Pokemon2DetailResponse(
                pokemon.id(),
                Long.parseLong(pokemon.speciesId()),
                pokemon.koName(),
                "image",
                pokemonTypeResponses,
                pokemonAbilityResponses,
                Integer.parseInt(pokemon.baseTotal()),
                stats.get(0),
                stats.get(1),
                stats.get(2),
                stats.get(3),
                stats.get(4),
                stats.get(5),
                Boolean.valueOf(pokemon.legendary()),
                Boolean.valueOf(pokemon.subLegendary()),
                Boolean.valueOf(pokemon.mythical()),
                Boolean.valueOf(pokemon.canChangeForm()),
                Double.parseDouble(pokemon.weight()),
                Double.parseDouble(pokemon.height()),
                evolutionResponse,
                moveResponse,
                eggMoveResponse,
                biomeResponse
        );
    }

    private List<PokemonAbilityResponse> createAbilityResponse(Pokemon pokemon) {
        Ability passive = Ability.findById(pokemon.abilityPassive());
        Ability hidden = Ability.findById(pokemon.abilityHidden());
        Ability ability1 = Ability.findById(pokemon.ability1());
        Ability ability2 = Ability.findById(pokemon.ability2());

        return List.of(
                PokemonAbilityResponse.from(passive.getName(), passive.getDescription(), true, false),
                PokemonAbilityResponse.from(hidden.getName(), hidden.getDescription(), false, true),
                PokemonAbilityResponse.from(ability1.getName(), ability1.getDescription(), false, false),
                PokemonAbilityResponse.from(ability2.getName(), ability2.getDescription(), false, false)
        );
    }

    private List<PokemonTypeResponse> createTypeResponse(Pokemon pokemon) {
        Type type1 = Type.findById(pokemon.type1());
        Type type2 = Type.findById(pokemon.type2());
        return List.of(
                PokemonTypeResponse.of(type1.getId(), type1.getName()),
                PokemonTypeResponse.of(type2.getId(), type2.getName())
        );
    }

    private List<BiomeResponse> createBiomeResponse(List<String> biomes) {
        return biomes.stream()
                .map(BiomeResponse::from)
                .toList();
    }

    private List<MoveResponse> createEggMoveResponse(List<String> moves) {
        return moves.stream()
                .map(moveRepository::findById)
                .map(move -> MoveResponse.from(move, 1))
                .toList();
    }

    private List<MoveResponse> createMoveResponse(List<String> moves) {
        return IntStream.iterate(0, index -> index + 2)
                .limit(moves.size() / 2)
                .mapToObj(index -> MoveResponse.from(
                        moveRepository.findById(moves.get(index)),
                        Integer.parseInt(moves.get(index + 1)))
                )
                .toList();
    }
}
