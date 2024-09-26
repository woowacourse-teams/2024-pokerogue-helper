package com.pokerogue.helper.pokemon.service;


import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.repository.InMemoryAbilityRepository;
import com.pokerogue.helper.battle.BattleMove;
import com.pokerogue.helper.battle.BattleMoveRepository;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.repository.InMemoryBiomeRepository;
import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.LevelMove;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.dto.MoveResponse;
import com.pokerogue.helper.pokemon.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon.dto.PokemonBiomeResponse;
import com.pokerogue.helper.pokemon.dto.PokemonDetailResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final S3Service s3Service;

    private final PokemonRepository pokemonRepository;
    private final BattleMoveRepository battleMoveRepository;
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
        findAllCache = pokemonRepository.findAll()
                .stream()
                .map(pokemon -> PokemonResponse.from(
                        pokemon,
                        s3Service.getPokemonImageFromS3(pokemon.getId()),
                        s3Service.getPokemonBackImageFromS3(pokemon.getId()),
                        createTypeResponse(pokemon)))
                .sorted(Comparator.comparingLong(PokemonResponse::pokedexNumber))
                .toList();
    }

    private List<PokemonTypeResponse> createTypeResponse(Pokemon pokemon) {
        return pokemon.getTypes()
                .stream()
                .map(type -> new PokemonTypeResponse(
                        type.getKoName(),
                        type.getImage()
                ))
                .toList();
    }

    public PokemonDetailResponse findById(String id) {
        if (findByIdCache.isEmpty()) {
            initFindByIdCache();
        }

        return findByIdCache.get(id);
    }

    private void initFindByIdCache() {
        List<PokemonDetailResponse> pokemonDetailResponse = pokemonRepository.findAll()
                .stream()
                .map(this::toPokemonDetailResponse)
                .toList();

        findByIdCache = pokemonDetailResponse.stream()
                .collect(Collectors.toMap(PokemonDetailResponse::id, Function.identity()));
    }

    private PokemonDetailResponse toPokemonDetailResponse(Pokemon pokemon) {
        List<PokemonTypeResponse> pokemonTypeResponses = createTypeResponse(pokemon);
        List<PokemonAbilityResponse> pokemonAbilityResponses = createAbilityResponse(pokemon);
        EvolutionResponses evolutionResponses = createEvolutionResponse(pokemon);
        List<MoveResponse> moveResponse = createMoveResponse(pokemon.getLevelMoves());
        List<MoveResponse> eggMoveResponse = createEggMoveResponse(pokemon.getEggMoveIds());
        List<PokemonBiomeResponse> biomeResponse = createBiomeResponse(pokemon.getBiomeIds());

        return new PokemonDetailResponse(
                pokemon.getId(),
                (long) pokemon.getPokedexNumber(),
                pokemon.getKoName(),
                s3Service.getPokemonImageFromS3(pokemon.getImageId()),
                pokemonTypeResponses,
                pokemonAbilityResponses,
                pokemon.getBaseTotal(),
                pokemon.getHp(),
                pokemon.getAttack(),
                pokemon.getDefense(),
                pokemon.getSpecialAttack(),
                pokemon.getSpecialDefense(),
                pokemon.getSpeed(),
                pokemon.isLegendary(),
                pokemon.isSubLegendary(),
                pokemon.isMythical(),
                pokemon.isCanChangeForm(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                evolutionResponses,
                moveResponse,
                eggMoveResponse,
                biomeResponse
        );
    }

    private EvolutionResponses createEvolutionResponse(Pokemon pokemon) {
        List<Evolution> evolutions = pokemon.getEvolutions();
        final int currentDepth = IntStream.range(0, evolutions.size())
                .filter(depth -> evolutions.get(depth).getFrom().equals(pokemon.getId()))
                .findAny()
                .orElse(0);
        return new EvolutionResponses(currentDepth, getEvolutionResponses(evolutions, pokemon.getId(),
                s3Service.getPokemonImageFromS3(pokemon.getImageId())));
    }

    private List<EvolutionResponse> getEvolutionResponses(
            List<Evolution> evolutions,
            String pokemonId,
            String imageId
    ) {
        List<EvolutionResponse> evolutionResponses = new ArrayList<>();
        for (int depth = 0; depth < evolutions.size(); depth++) {
            evolutionResponses.add(EvolutionResponse.from(
                    evolutions.get(depth),
                    depth,
                    pokemonId,
                    s3Service.getPokemonImageFromS3(imageId)
            ));
        }
        return evolutionResponses;
    }


    private List<PokemonAbilityResponse> createAbilityResponse(Pokemon pokemon) {
        List<PokemonAbilityResponse> responses = pokemon.getNormalAbilityIds().stream()
                .map(inMemoryAbilityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ability -> new PokemonAbilityResponse(
                        ability.getId(),
                        ability.getKoName(),
                        ability.getDescription(),
                        false,
                        false
                ))
                .collect(Collectors.toList());

        Optional<Ability> hidden = inMemoryAbilityRepository.findById(pokemon.getHiddenAbilityId());
        Optional<Ability> passive = inMemoryAbilityRepository.findById(pokemon.getPassiveAbilityId());

        passive.ifPresent(
                ability -> responses.add(new PokemonAbilityResponse(
                        ability.getId(),
                        ability.getKoName(),
                        ability.getDescription(),
                        true,
                        false))
        );

        hidden.ifPresent(
                ability -> responses.add(new PokemonAbilityResponse(
                        ability.getId(),
                        ability.getKoName(),
                        ability.getDescription(),
                        false,
                        true))
        );

        return responses;
    }


    private List<PokemonBiomeResponse> createBiomeResponse(List<String> biomes) {
        return biomes.stream()
                .map(inMemoryBiomeRepository::findById)
                .map(this::getBiomeValue)
                .toList();
    }

    private PokemonBiomeResponse getBiomeValue(Optional<Biome> optional) {
        return optional.map(value -> new PokemonBiomeResponse(
                value.getId(),
                value.getKoName(),
                s3Service.getBiomeImageFromS3(value.getImage()))
        ).orElse(PokemonBiomeResponse.EMPTY);
    }

    private List<MoveResponse> createEggMoveResponse(List<String> moves) {
        return moves.stream()
                .map(battleMoveRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(move -> MoveResponse.from(move, 1, move.type().getImage()))
                .toList();
    }

    private List<MoveResponse> createMoveResponse(List<LevelMove> moves) {
        return moves.stream()
                .map(move -> {
                            BattleMove battleMove = battleMoveRepository.findById(move.getMoveId()).orElseThrow();
                            return new MoveResponse(
                                    battleMove.id(),
                                    battleMove.name(),
                                    move.getLevel(),
                                    battleMove.power(),
                                    battleMove.accuracy(),
                                    battleMove.type().getKoName(),
                                    battleMove.type().getImage(),
                                    battleMove.category().getName(),
                                    battleMove.category().getImage()
                            );
                        }
                )
                .toList();
    }
}
