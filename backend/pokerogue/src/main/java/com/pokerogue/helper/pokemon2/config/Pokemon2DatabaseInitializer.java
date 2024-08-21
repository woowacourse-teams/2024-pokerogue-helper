package com.pokerogue.helper.pokemon2.config;

import com.pokerogue.helper.pokemon2.data.EvolutionChain;
import com.pokerogue.helper.pokemon2.data.Move;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.data.Evolution;
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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Pokemon2DatabaseInitializer implements ApplicationRunner {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;
    private final EvolutionRepository evolutionRepository;

    List<String> pokemonKeys = List.of(
            "id",
            "speciesId",
            "speciesName",
            "formName",
            "nameKo",
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
    List<String> moveKeys = List.of(
            "id",
            "name",
            "effect",
            "power",
            "accuracy",
            "type",
            "category"
    );

    List<String> evolutionChainKeys = List.of(
            "from",
            "to",
            "level",
            "item",
            "condition"
    );

    @Override
    public void run(ApplicationArguments args) {
        try (
                InputStream inputStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream("pokemon.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            savePokemon(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("move-for-pokemon-response.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            saveMove(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("evolution-for-pokemon-response.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            saveEvolution(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveEvolution(BufferedReader br) throws IOException {
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
            List<String> normalForms = List.of("mega", "mega_x", "mega_y", "primal", "g_max", "e_max");

            if (normalForms.contains(regularize(pokemon.formName()))) {

                evolutionRepository.saveEdge(pokemon.id(),
                        new Evolution(pokemon.speciesName(), pokemon.id(),
                                "EMPTY", "EMPTY", "EMPTY"));
            }
        }

        createEvolutionChains();
    }

    private void createEvolutionChains() {

        List<String> evolutions = evolutionRepository.findAll()
                .keySet()
                .stream()
                .sorted(Comparator.comparing(r -> pokemon2Repository.findById(r)
                        .orElseThrow(()-> new IllegalArgumentException(""))
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
        List<String> evolveCondtions = Arrays.stream(values.split("@")).toList();

        return new Evolution(
                regularize(from),
                regularize(evolveCondtions.get(0)),
                regularize(evolveCondtions.get(1)),
                regularize(evolveCondtions.get(2)),
                evolveCondtions.get(3)
        );
    }

    private List<String> parseToken(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, "/");

        List<String> values = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken().strip();

            if (token.contains("undefined")) {
                token = "empty";
            }

            values.add(token);
        }

        return values;
    }

    private String regularize(String str) {
        return str.strip()
                .replace(" ", "_")
                .replace("-", "_")
                .toLowerCase();
    }

    private void savePokemon(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            List<String> tokens = parseToken(line);

            if (pokemonKeys.size() != tokens.size()) {
                throw new IllegalArgumentException(pokemonKeys.size() + " " + tokens.size() + "포켓몬 데이터가 잘못 되었습니다.");
            }

            Pokemon pokemon = createPokemon(tokens);
            pokemon2Repository.save(pokemon.id(), pokemon);
        }
    }

    private Pokemon createPokemon(List<String> values) {
        List<String> moves = Arrays.stream(values.get(22).split(","))
                .collect(Collectors.toList());
        for (int i = 0; i < moves.size(); i += 2) {
            moves.set(i, regularize(moves.get(i)));
        }
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
                regularize(values.get(11)),
                regularize(values.get(12)),
                regularize(values.get(13)),
                regularize(values.get(14)),
                regularize(values.get(15)),
                Arrays.stream(regularize(values.get(16)).split(",")).toList(),
                regularize(values.get(17)),
                regularize(values.get(18)),
                regularize(values.get(19)),
                regularize(values.get(20)),
                Arrays.stream(regularize(values.get(21)).split(",")).toList(),
                moves,
                Arrays.stream(regularize(values.get(23)).split(",")).toList()
        );
    }


    private void saveMove(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            List<String> tokens = parseToken(line);

            if (moveKeys.size() != tokens.size()) {
                throw new IllegalArgumentException(moveKeys.size() + " " + tokens.size() + "기술 데이터가 잘못 되었습니다.");
            }

            Move move = createMove(tokens);

            moveRepository.save(move.id(), move);
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
}
