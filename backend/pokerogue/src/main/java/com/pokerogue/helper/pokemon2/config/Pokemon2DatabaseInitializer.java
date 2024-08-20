package com.pokerogue.helper.pokemon2.config;

import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class Pokemon2DatabaseInitializer implements ApplicationRunner {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;

    List<String> moveKeys = List.of("id", "name", "nameAppend", "effect", "type", "defaultType", "category",
            "moveTarget", "power", "accuracy", "pp", "chance", "priority", "generation", "flags");
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

    @Override
    public void run(ApplicationArguments args) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pokemon.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>();
                StringTokenizer stringTokenizer = new StringTokenizer(line, "/");

                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken()
                            .strip()
                            .replaceAll("-", "_")
                            .replaceAll(" ", "_")
                            .toLowerCase();
                    if ("type.undefined".equals(token)) {
                        token = "empty";
                    }
                    values.add(token);
                }

                if (pokemonKeys.size() != values.size()) {
                    throw new IllegalArgumentException(pokemonKeys.size() + " " + values.size() + "데이터가 잘못 되었습니다.");
                }

                Pokemon pokemon = createPokemon(values);
                pokemon2Repository.save(pokemon.id(), pokemon);
            }
//            saveMove();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pokemon createPokemon(List<String> values) {
        return new Pokemon(
                values.get(0),
                values.get(1),
                values.get(2),
                values.get(3),
                values.get(4),
                values.get(5),
                values.get(6),
                values.get(7),
                values.get(8),
                values.get(9),
                values.get(10),
                values.get(11),
                values.get(12),
                values.get(13),
                values.get(14),
                values.get(15),
                Arrays.stream(values.get(16).split(",")).toList(),
                values.get(17),
                values.get(18),
                values.get(19),
                values.get(20),
                Arrays.stream(values.get(21).split(",")).toList(),
                Arrays.stream(values.get(22).split(",")).toList(),
                Arrays.stream(values.get(23).split(",")).toList()
        );
    }

    private void saveMove() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("move.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>();
                StringTokenizer stringTokenizer = new StringTokenizer(line, "/");

                while (stringTokenizer.hasMoreTokens()) {
                    values.add(stringTokenizer.nextToken().strip());
                }

                Map<String, String> map = IntStream.range(0, values.size())
                        .boxed()
                        .collect(Collectors.toMap(moveKeys::get, values::get));

                moveRepository.save(map.get("name"), map);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
