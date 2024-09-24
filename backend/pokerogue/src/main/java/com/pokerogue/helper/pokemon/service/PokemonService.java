package com.pokerogue.helper.pokemon.service;


import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.repository.InMemoryAbilityRepository;
import com.pokerogue.helper.battle.BattleMoveRepository;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.repository.InMemoryBiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.EvolutionChain;
import com.pokerogue.helper.pokemon.data.EvolutionItem;
import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import com.pokerogue.helper.pokemon.data.Type;
import com.pokerogue.helper.pokemon.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.dto.MoveResponse;
import com.pokerogue.helper.pokemon.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon.dto.PokemonBiomeResponse;
import com.pokerogue.helper.pokemon.dto.PokemonDetailResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon.repository.InMemoryPokemonRepository;
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
public class PokemonService {

    private final S3Service s3Service;
    private final InMemoryPokemonRepository inMemoryPokemonRepository;
    private final BattleMoveRepository battleMoveRepository;
    private final EvolutionRepository evolutionRepository;
    private final InMemoryBiomeRepository inMemoryBiomeRepository;
    private final InMemoryAbilityRepository inMemoryAbilityRepository;

    private List<PokemonResponse> findAllCache = List.of();
    private Map<String, PokemonDetailResponse> findByIdCache = new HashMap<>();

    public List<PokemonResponse> findAll() {
        if (findAllCache.isEmpty()) {
            initFindAllCache();
        }
        return findAllCache;
    }

    private void initFindAllCache() {
        findAllCache = inMemoryPokemonRepository.findAll().values().stream()
                .map(pokemon -> PokemonResponse.from(
                        pokemon,
                        s3Service.getPokemonImageFromS3(pokemon.id()),
                        s3Service.getPokemonBackImageFromS3(pokemon.id()),
                        createTypeResponse(pokemon)
                ))
                .sorted(Comparator.comparingLong(PokemonResponse::pokedexNumber))
                .toList();
    }

    public PokemonDetailResponse findById(String id) {
        if (findByIdCache.isEmpty()) {
            initFindByIdCache();
        }
        return findByIdCache.get(id);
    }

    private void initFindByIdCache() {
        List<PokemonDetailResponse> pokemonDetailRespons = inMemoryPokemonRepository.findAll().values()
                .stream()
                .map(this::toPokemon2DetailResponse)
                .toList();

        findByIdCache = pokemonDetailRespons.stream().collect(
                Collectors.toMap(
                        PokemonDetailResponse::id,
                        Function.identity()
                ));
    }

    private PokemonDetailResponse toPokemon2DetailResponse(InMemoryPokemon inMemoryPokemon) {
        List<PokemonTypeResponse> pokemonTypeResponses = createTypeResponse(inMemoryPokemon);
        List<PokemonAbilityResponse> pokemonAbilityResponses = createAbilityResponse(inMemoryPokemon);
        EvolutionResponses evolutionResponses = createEvolutionResponse(inMemoryPokemon);
        List<MoveResponse> moveResponse = createMoveResponse(inMemoryPokemon.moves());
        List<MoveResponse> eggMoveResponse = createEggMoveResponse(inMemoryPokemon.eggMoves());
        List<PokemonBiomeResponse> biomeResponse = createBiomeResponse(inMemoryPokemon.biomes());

        return new PokemonDetailResponse(
                inMemoryPokemon.id(),
                Long.parseLong(inMemoryPokemon.speciesId()),
                inMemoryPokemon.koName(),
                s3Service.getPokemonImageFromS3(inMemoryPokemon.id()),
                pokemonTypeResponses,
                pokemonAbilityResponses,
                inMemoryPokemon.baseTotal(),
                inMemoryPokemon.hp(),
                inMemoryPokemon.attack(),
                inMemoryPokemon.defense(),
                inMemoryPokemon.specialAttack(),
                inMemoryPokemon.specialDefense(),
                inMemoryPokemon.speed(),
                inMemoryPokemon.legendary(),
                inMemoryPokemon.subLegendary(),
                inMemoryPokemon.mythical(),
                inMemoryPokemon.canChangeForm(),
                inMemoryPokemon.weight(),
                inMemoryPokemon.height(),
                evolutionResponses,
                moveResponse,
                eggMoveResponse,
                biomeResponse
        );
    }

    private EvolutionResponses createEvolutionResponse(InMemoryPokemon inMemoryPokemon) {
        EvolutionChain chain = evolutionRepository.findEvolutionChainById(inMemoryPokemon.id())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.EVOLUTION_NOT_FOUND));

        int currentStage = IntStream.range(0, chain.getChain().size())
                .filter(i -> chain.getChain().get(i).stream().anyMatch(r -> r.equals(inMemoryPokemon.id())))
                .sum();

        return new EvolutionResponses(
                currentStage,
                createStages(chain)
        );
    }

    private List<EvolutionResponse> createStages(EvolutionChain evolutionChain) {
        List<List<String>> chain = evolutionChain.getChain();
        List<EvolutionResponse> ret = new ArrayList<>();

        InMemoryPokemon firstInMemoryPokemon = inMemoryPokemonRepository.findById(chain.get(0).get(0))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

        ret.add(new EvolutionResponse(
                firstInMemoryPokemon.id(),
                firstInMemoryPokemon.koName(),
                1,
                0,
                "",
                "",
                s3Service.getPokemonImageFromS3(firstInMemoryPokemon.id())
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
                    InMemoryPokemon inMemoryPokemon = inMemoryPokemonRepository.findById(evolution.to())
                            .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
                    ret.add(new EvolutionResponse(
                            inMemoryPokemon.id(),
                            inMemoryPokemon.koName(),
                            Integer.parseInt(evolution.level()),
                            i + 1,
                            EvolutionItem.findById(evolution.item()).getKoName(),
                            evolution.condition(),
                            s3Service.getPokemonImageFromS3(inMemoryPokemon.id())
                    ));
                }
            }
        }
        return ret;
    }

    private List<PokemonAbilityResponse> createAbilityResponse(InMemoryPokemon inMemoryPokemon) {
        List<PokemonAbilityResponse> ret = new ArrayList<>();

        Ability passive = inMemoryAbilityRepository.findById(inMemoryPokemon.abilityPassive())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        ret.add(new PokemonAbilityResponse(
                inMemoryPokemon.abilityPassive(), passive.getName(), passive.getDescription(),
                true, false
        ));

        Ability ability1 = inMemoryAbilityRepository.findById(inMemoryPokemon.ability1())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        Ability ability2 = inMemoryAbilityRepository.findById(inMemoryPokemon.ability2())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        List<PokemonAbilityResponse> defaultAbilities = Stream.of(ability1, ability2)
                .distinct()
                .map(ability -> new PokemonAbilityResponse(
                        inMemoryPokemon.ability1(), ability.getName(),
                        ability.getDescription(), false, false)
                )
                .toList();

        ret.addAll(defaultAbilities);

        if (!inMemoryPokemon.abilityHidden().isEmpty()) {
            Ability hidden = inMemoryAbilityRepository.findById(inMemoryPokemon.abilityHidden())
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
            ret.add(new PokemonAbilityResponse(inMemoryPokemon.abilityHidden(), hidden.getName(), hidden.getDescription(),
                    false,
                    true));

            for (int i = 1; i < ret.size() - 1; i++) {
                if (inMemoryAbilityRepository.findById(ret.get(i).id())
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND)) == hidden
                ) {
                    ret.remove(i);
                    break;
                }
            }
        }

        return ret;
    }

    private List<PokemonTypeResponse> createTypeResponse(InMemoryPokemon inMemoryPokemon) {
        Type firstType = Type.findById(inMemoryPokemon.firstType());
        Type secondType = Type.findById(inMemoryPokemon.secondType());
        return Stream.of(firstType, secondType)
                .distinct()
                .filter(type -> type != Type.EMPTY)
                .map(type -> new PokemonTypeResponse(type.getName(),
                        s3Service.getPokerogueTypeImageFromS3(type.getId().toLowerCase())))
                .toList();
    }

    private List<PokemonBiomeResponse> createBiomeResponse(List<String> biomes) {
        return biomes.stream()
                .map(id -> {
                            if (id.isEmpty()) {
                                return new PokemonBiomeResponse("", "", "");
                            }
                            Biome biome = inMemoryBiomeRepository.findById(id).orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));
                            return new PokemonBiomeResponse(
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
                .map(r -> battleMoveRepository.findById(r).orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND)))
                .map(move -> MoveResponse.from(move, 1,
                        s3Service.getPokerogueTypeImageFromS3(battleMoveRepository.findById(move.id())
                                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND))
                                .type().getName())))
                .toList();
    }

    private List<MoveResponse> createMoveResponse(List<String> moves) {
        return IntStream.iterate(0, index -> index + 2)
                .limit(moves.size() / 2)
                .mapToObj(index -> MoveResponse.from(
                        battleMoveRepository.findById(moves.get(index)).orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND)),
                        Integer.parseInt(moves.get(index + 1)),
                        s3Service.getPokerogueTypeImageFromS3(
                                battleMoveRepository.findById(moves.get(index))
                                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND))
                                        .type().getName())
                ))
                .toList();
    }
}
