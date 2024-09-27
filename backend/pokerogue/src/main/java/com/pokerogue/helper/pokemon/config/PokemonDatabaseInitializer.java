package com.pokerogue.helper.pokemon.config;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.EvolutionChain;
import com.pokerogue.helper.pokemon.data.EvolutionItem;
import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import com.pokerogue.helper.pokemon.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon.repository.InMemoryPokemonRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class PokemonDatabaseInitializer implements ApplicationRunner {

    private static final int FIRST_LINE_NUMBER = 3;
    private static final String FIELD_DELIMITER = "/";
    private static final String LIST_DELIMITER = ",";

    private final InMemoryPokemonRepository inMemoryPokemonRepository;
    private final EvolutionRepository evolutionRepository;
    private final List<String> pokemonKeys = List.of(
            "id",
            "speciesId",
            "nameKo",
            "speciesName",
            "formName",
            "firstType",
            "secondType",
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
        save("data/pokemon/pokemon.txt", this::savePokemon);
        save("data/pokemon/evolution-for-pokemon-response.txt", this::saveEvolution);
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
            Map<String, List<String>> technicalMachineMoveInfos = savetechnicalMachineMoves("data/battle/tms.txt");
            String line;
            while ((line = br.readLine()) != null) {
                List<String> tokens = parseToken(line);

                if (pokemonKeys.size() != tokens.size()) {
                    throw new IllegalArgumentException(pokemonKeys.size() + " " + tokens.size() + "포켓몬 데이터가 잘못 되었습니다.");
                }

                InMemoryPokemon inMemoryPokemon = createPokemon(tokens, technicalMachineMoveInfos);
                inMemoryPokemonRepository.save(inMemoryPokemon.id(), inMemoryPokemon);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<String>> savetechnicalMachineMoves(String path) {
        Map<String, List<String>> technicalMachineMoveInfos = new HashMap<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            int lineCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount < FIRST_LINE_NUMBER) {
                    continue;
                }
                List<String> fields = splitFields(line);
                List<String> moveIds = Arrays.stream(fields.get(2).split(LIST_DELIMITER))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();
                technicalMachineMoveInfos.put(fields.get(0), moveIds);
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getMessage(), e);
        }
        return technicalMachineMoveInfos;
    }

    private List<String> splitFields(String line) {
        return Arrays.stream(line.split(FIELD_DELIMITER))
                .map(String::trim)
                .map(this::regularizeEmptyField)
                .toList();
    }

    private String regularizeEmptyField(String field) {
        if (field.equals("EMPTY")) {
            return "";
        }
        return field;
    }

    private InMemoryPokemon createPokemon(List<String> values, Map<String, List<String>> technicalMachineMoveInfos) {
        List<String> moves = Arrays.stream(values.get(22).split(","))
                .collect(Collectors.toList());
        for (int i = 0; i < moves.size(); i += 2) {
            moves.set(i, regularize(moves.get(i)));
        }

        List<Integer> stats = Arrays.stream(regularize(values.get(18)).split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();

        List<String> technicalMachineMoves = technicalMachineMoveInfos.get(regularize(values.get(1)));
        return new InMemoryPokemon(
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
                technicalMachineMoves,
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

            for (InMemoryPokemon inMemoryPokemon : inMemoryPokemonRepository.findAll().values()) {
                List<String> normalForms = List.of("mega", "mega_x", "mega_y", "primal", "gigantamax", "eternamax");
                if (normalForms.contains(regularize(inMemoryPokemon.formName()))) {

                    evolutionRepository.saveEdge(
                            inMemoryPokemon.speciesName(),
                            new Evolution(inMemoryPokemon.speciesName(), "1", inMemoryPokemon.id(),
                                    EvolutionItem.BLACK_AUGURITE, inMemoryPokemon.formName())
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
                .sorted(Comparator.comparing(r -> inMemoryPokemonRepository.findById(r)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND))
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

        for (String from : inMemoryPokemonRepository.findAll().keySet()) {
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
                EvolutionItem.BLACK_AUGURITE,
                regularize(evolveConditions.get(3))
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
