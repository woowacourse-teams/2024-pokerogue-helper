package com.pokerogue.helper.pokemon2.service;


import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.pokemon2.data.Ability;
import com.pokerogue.helper.pokemon2.data.Biome;
import com.pokerogue.helper.pokemon2.data.Evolution;
import com.pokerogue.helper.pokemon2.data.EvolutionChain;
import com.pokerogue.helper.pokemon2.data.EvolutionItem;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.data.Type;
import com.pokerogue.helper.pokemon2.dto.BiomeResponse;
import com.pokerogue.helper.pokemon2.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon2.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon2.dto.MoveResponse;
import com.pokerogue.helper.pokemon2.dto.Pokemon2DetailResponse;
import com.pokerogue.helper.pokemon2.dto.Pokemon2Response;
import com.pokerogue.helper.pokemon2.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon2.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Pokemon2Service {

    private final S3Service s3Service;
    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;
    private final EvolutionRepository evolutionRepository;

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
                .map(pokemon -> Pokemon2Response.from(
                        pokemon,
                        s3Service.getPokemonImageFromS3(pokemon.id()),
                        s3Service.getPokemonBackImageFromS3(pokemon.id()),
                        createTypeResponse(pokemon)
                ))
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
        EvolutionResponses evolutionResponses = createEvolutionResponse(pokemon);
        List<MoveResponse> moveResponse = createMoveResponse(pokemon.moves());
        List<MoveResponse> eggMoveResponse = createEggMoveResponse(pokemon.eggMoves());
        List<BiomeResponse> biomeResponse = createBiomeResponse(pokemon.biomes());

        return new Pokemon2DetailResponse(
                pokemon.id(),
                Long.parseLong(pokemon.speciesId()),
                pokemon.koName(),
                s3Service.getPokemonImageFromS3(pokemon.id()),
                pokemonTypeResponses,
                pokemonAbilityResponses,
                pokemon.baseTotal(),
                pokemon.hp(),
                pokemon.attack(),
                pokemon.defense(),
                pokemon.specialAttack(),
                pokemon.specialDefense(),
                pokemon.speed(),
                pokemon.legendary(),
                pokemon.subLegendary(),
                pokemon.mythical(),
                pokemon.canChangeForm(),
                pokemon.weight(),
                pokemon.height(),
                evolutionResponses,
                moveResponse,
                eggMoveResponse,
                biomeResponse
        );
    }

    private EvolutionResponses createEvolutionResponse(Pokemon pokemon) {
        EvolutionChain chain = evolutionRepository.findEvolutionChainById(pokemon.id())
                .orElseThrow(() -> new IllegalArgumentException());

        int currentStage = IntStream.range(0, chain.getChain().size())
                .filter(i -> chain.getChain().get(i).stream().anyMatch(r -> r.equals(pokemon.id())))
                .sum();

        return new EvolutionResponses(
                currentStage,
                createStages(chain)
        );
    }

    private List<EvolutionResponse> createStages(EvolutionChain evolutionChain) {
        List<List<String>> chain = evolutionChain.getChain();
        List<EvolutionResponse> ret = new ArrayList<>();

        Pokemon firstPokemon = pokemon2Repository.findById(chain.get(0).get(0))
                .orElseThrow(() -> new IllegalArgumentException());

        ret.add(new EvolutionResponse(
                firstPokemon.id(),
                firstPokemon.koName(),
                1,
                0,
                "",
                "",
                s3Service.getPokemonImageFromS3(firstPokemon.id())
        ));

        for (int i = 0; i < chain.size() - 1; i++) {
            List<String> stage = chain.get(i);
            for (String id : stage) {
                List<Evolution> evolutions = evolutionRepository.findEdgeById(id)
                        .orElse(null);

                if (evolutions == null) {
                    continue;
                }

                for (Evolution evolution : evolutions) {
                    Pokemon pokemon = pokemon2Repository.findById(evolution.to())
                            .orElseThrow(() -> new IllegalArgumentException(""));
                    ret.add(new EvolutionResponse(
                            pokemon.id(),
                            pokemon.koName(),
                            Integer.parseInt(evolution.level()),
                            i + 1,
                            EvolutionItem.findById(evolution.item()).getKoName(),
                            evolution.condition(),
                            s3Service.getPokemonImageFromS3(pokemon.id())
                    ));
                }
            }
        }
        return ret;
    }

    private List<PokemonAbilityResponse> createAbilityResponse(Pokemon pokemon) {
        Ability passive = Ability.findById(pokemon.abilityPassive());
        Ability hidden = Ability.findById(pokemon.abilityHidden());
        Ability ability1 = Ability.findById(pokemon.ability1());
        Ability ability2 = Ability.findById(pokemon.ability2());

        return List.of(
                new PokemonAbilityResponse(pokemon.abilityPassive(), passive.getName(), passive.getDescription(), true,
                        false),
                new PokemonAbilityResponse(pokemon.abilityHidden(), hidden.getName(), hidden.getDescription(), false,
                        true),
                new PokemonAbilityResponse(pokemon.ability1(), ability1.getName(), ability1.getDescription(), false,
                        false),
                new PokemonAbilityResponse(pokemon.ability2(), ability2.getName(), ability2.getDescription(), false,
                        false)
        );
    }

    private List<PokemonTypeResponse> createTypeResponse(Pokemon pokemon) {
        Type type1 = Type.findById(pokemon.type1());
        Type type2 = Type.findById(pokemon.type2());
        return Stream.of(type1, type2)
                .distinct()
                .filter(type -> type != Type.EMPTY)
                .map(type -> new PokemonTypeResponse(type.getName(),
                        s3Service.getPokerogueTypeImageFromS3(type.getId().toLowerCase())))
                .toList()
                ;
    }

    private List<BiomeResponse> createBiomeResponse(List<String> biomes) {
        return biomes.stream()
                .map(id -> {
                            Biome biome = Biome.findById(id);
                            return new BiomeResponse(
                                    id,
                                    biome.getName(),
                                    s3Service.getBiomeImageFromS3(id)
                            );
                        }
                )
                .toList();
    }

    private List<MoveResponse> createEggMoveResponse(List<String> moves) {
        return moves.stream()
                .map(r -> moveRepository.findById(r).orElseThrow(() -> new IllegalArgumentException()))
                .map(move -> MoveResponse.from(move, 1,
                        s3Service.getPokerogueTypeImageFromS3(moveRepository.findById(move.id())
                                .orElseThrow(() -> new IllegalArgumentException())
                                .type())))
                .toList();
    }

    private List<MoveResponse> createMoveResponse(List<String> moves) {
        return IntStream.iterate(0, index -> index + 2)
                .limit(moves.size() / 2)
                .mapToObj(index -> MoveResponse.from(
                        moveRepository.findById(moves.get(index)).orElseThrow(),
                        Integer.parseInt(moves.get(index + 1)),
                        s3Service.getPokerogueTypeImageFromS3(
                                moveRepository.findById(moves.get(index)).orElseThrow().type())
                ))
                .toList();
    }
}
