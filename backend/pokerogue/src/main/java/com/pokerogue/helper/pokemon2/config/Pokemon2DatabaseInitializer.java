package com.pokerogue.helper.pokemon2.config;

import com.pokerogue.helper.ability2.data.Ability;
import com.pokerogue.helper.ability2.data.AbilityInfo;
import com.pokerogue.helper.ability2.repository.AbilityRepository;
import com.pokerogue.helper.pokemon2.data.Evolution;
import com.pokerogue.helper.pokemon2.data.EvolutionChain;
import com.pokerogue.helper.pokemon2.data.Move;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pokemon2DatabaseInitializer implements ApplicationRunner {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;
    private final EvolutionRepository evolutionRepository;
    private final AbilityRepository abilityRepository;
    private final List<String> pokemonKeys = List.of(
            "id",
            "speciesId",
            "nameKo",
            "speciesName",
            "formName",
            "type1",
            "type2",
            "ability1",
            "ability2",
            "abilityHidden",
            "abilityPassive",
            "generation",
            "legendary",
            "subLegendary",
            "mythical",
            "canChangeForm",
            "evolutionLevel",
            "baseTotal",
            "baseStats",
            "height",
            "weight",
            "eggMoves",
            "moves",
            "biomes"
    );
    private final List<String> moveKeys = List.of(
            "id",
            "name",
            "effect",
            "power",
            "accuracy",
            "type",
            "category"
    );

    private final List<String> evolutionChainKeys = List.of(
            "from",
            "to",
            "level",
            "item",
            "condition"
    );

    @Override
    public void run(ApplicationArguments args) {
        save("pokemon.txt", this::savePokemon);
        save("move-for-pokemon-response.txt", this::saveMove);
        save("evolution-for-pokemon-response.txt", this::saveEvolution);
        List<AbilityInfo> abilityInfos = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ability.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                String abilityInfo = bufferedReader.readLine();
                if (abilityInfo == null) {
                    break;
                }
                abilityInfos.add(new AbilityInfo(abilityInfo));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        abilityInfos.stream()
                .map(abilityInfo -> new Ability(
                        abilityInfo.getId(),
                        abilityInfo.getName(),
                        abilityInfo.getDescription(),
                        getAbilityPokemon(abilityInfo.getPokemons()))
                ).forEach(abilityRepository::save);
    }

    private List<Pokemon> getAbilityPokemon(List<String> pokemons) {
        List<Pokemon> abilityPokemons = new ArrayList<>();
        for (int i = 0; i < pokemons.size(); i++) {
            String pokemonId = pokemons.get(i);

            Pokemon pokemon = pokemon2Repository.findById(pokemonId)
                    .orElseThrow();

            abilityPokemons.add(pokemon);
        }

        return abilityPokemons;
    }

    private void save(String file, Consumer<BufferedReader> consumer) {
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            consumer.accept(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void savePokemon(BufferedReader br) {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> tokens = parseToken(line);

                if (pokemonKeys.size() != tokens.size()) {
                    throw new IllegalArgumentException(pokemonKeys.size() + " " + tokens.size() + "포켓몬 데이터가 잘못 되었습니다.");
                }

                Pokemon pokemon = createPokemon(tokens);
                pokemon2Repository.save(pokemon.id(), pokemon);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pokemon createPokemon(List<String> values) {
        List<String> moves = Arrays.stream(values.get(22).split(","))
                .collect(Collectors.toList());
        for (int i = 0; i < moves.size(); i += 2) {
            moves.set(i, regularize(moves.get(i)));
        }

        List<Integer> stats = Arrays.stream(regularize(values.get(18)).split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();

        return new Pokemon(
                regularize(values.get(0)),
                regularize(values.get(1)),
                regularize(values.get(2)),
                regularize(values.get(3)),
                regularize(values.get(4)),
                regularize(values.get(5)),
                regularize(values.get(6)),
                regularize(values.get(7)),
                regularize(values.get(8)),
                regularize(values.get(9)),
                regularize(values.get(10)),
                Integer.valueOf(regularize(values.get(11))),
                Boolean.valueOf(regularize(values.get(12))),
                Boolean.valueOf(regularize(values.get(13))),
                Boolean.valueOf(regularize(values.get(14))),
                Boolean.valueOf(regularize(values.get(15))),
                Arrays.stream(regularize(values.get(16)).split(",")).toList(),
                Integer.parseInt(values.get(17)),
                stats.get(0),
                stats.get(1),
                stats.get(2),
                stats.get(3),
                stats.get(4),
                stats.get(5),
                Double.parseDouble(values.get(19)),
                Double.parseDouble(values.get(20)),
                Arrays.stream(regularize(values.get(21)).split(",")).toList(),
                moves,
                Arrays.stream(regularize(values.get(23)).split(",")).toList()
        );
    }

    private void saveEvolution(BufferedReader br) {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> tokens = parseToken(line);
                String speciesName = regularize(tokens.get(0));

                for (int i = 1; i < tokens.size(); i++) {
                    Evolution evolution = createEvolution(speciesName, tokens.get(i));
                    evolutionRepository.saveEdge(speciesName, evolution);
                }
            }

            for (Pokemon pokemon : pokemon2Repository.findAll().values()) {
                List<String> normalForms = List.of("mega", "mega_x", "mega_y", "primal", "gigantamax", "eternamax");
                if (normalForms.contains(regularize(pokemon.formName()))) {

                    evolutionRepository.saveEdge(
                            pokemon.speciesName(),
                            new Evolution(pokemon.speciesName(), "1", pokemon.id(), "", pokemon.formName())
                    );
                }
            }
            createEvolutionChains();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createEvolutionChains() {
        List<String> evolutions = evolutionRepository.findAll()
                .keySet()
                .stream()
                .sorted(Comparator.comparing(r -> pokemon2Repository.findById(r)
                        .orElseThrow(() -> new IllegalArgumentException(""))
                        .speciesId()
                ))
                .toList();

        Set<String> vis = new HashSet<>();
        for (String from : evolutions) {
            if (vis.contains(from)) {
                continue;
            }
            EvolutionChain chain = new EvolutionChain();
            createChain(vis, from, chain);
            evolutionRepository.saveChain(from, chain);
        }

        for (String from : pokemon2Repository.findAll().keySet()) {
            if (
                    evolutionRepository.findEvolutionChainById(from).isEmpty() ||
                    evolutionRepository.findEvolutionChainById(from).get().getChain().isEmpty()
            ) {
                evolutionRepository.saveChain(from, new EvolutionChain(List.of(List.of(from))));
            }
        }

    }

    private void createChain(Set<String> vis, String from, EvolutionChain chain) {
        Queue<String> q = new LinkedList<>();
        vis.add(from);
        q.add(from);
        int depth = 0;
        while (!q.isEmpty()) {
            int qs = q.size();
            while (qs-- > 0) {
                String cur = q.poll();
                Optional<List<Evolution>> edges = evolutionRepository.findEdgeById(cur);
                if (edges.isEmpty()) {
                    continue;
                }
                for (Evolution edge : edges.get()) {
                    if (vis.contains(edge.to())) {
                        continue;
                    }
                    q.add(edge.to());
                    vis.add(edge.to());
                    chain.push(edge, depth);
                }
            }
            depth++;
        }

        chain.getAllIds().forEach(id -> evolutionRepository.saveChain(id, chain));
    }

    private Evolution createEvolution(String from, String values) {
        List<String> evolveConditions = Arrays.stream(values.split(",")).toList();

        return new Evolution(
                regularize(from),
                regularize(evolveConditions.get(0)),
                regularize(evolveConditions.get(1)),
                regularize(evolveConditions.get(2)),
                regularize(evolveConditions.get(3))
        );
    }


    private void saveMove(BufferedReader br) {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> tokens = parseToken(line);

                if (moveKeys.size() != tokens.size()) {
                    throw new IllegalArgumentException(moveKeys.size() + " " + tokens.size() + "기술 데이터가 잘못 되었습니다.");
                }

                Move move = createMove(tokens);

                moveRepository.save(move.id(), move);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Move createMove(List<String> tokens) {
        return new Move(
                regularize(tokens.get(0)),
                regularize(tokens.get(1)),
                tokens.get(2),
                tokens.get(3),
                tokens.get(4),
                regularize(tokens.get(5)),
                regularize(tokens.get(6))
        );
    }

    private String regularize(String str) {
        String ret = str.strip()
                .replace(" ", "_")
                .replace("-", "_")
                .toLowerCase();

        if (List.of("empty", "type.undefined", "none").contains(ret)) {
            return "";
        }

        return ret;
    }

    private List<String> parseToken(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, "/");

        List<String> values = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken().strip();

            if (token.contains("undefined")) {
                token = "";
            }

            values.add(token);
        }

        return values;
    }
}
