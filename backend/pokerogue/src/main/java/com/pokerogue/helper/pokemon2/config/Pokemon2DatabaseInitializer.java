package com.pokerogue.helper.pokemon2.config;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.pokemon2.data.Move;
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

    List<String> moveKeys = List.of(
            "id",
            "name",
            "effect",
            "power",
            "accuracy",
            "type",
            "category"
    );
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
        return str.replace(" ", "_")
                .replace("-", "_")
                .toLowerCase();
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
