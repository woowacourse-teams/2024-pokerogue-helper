package com.pokerogue.helper.global.config;

import com.pokerogue.helper.pokemon2.MoveRepository;
import com.pokerogue.helper.pokemon2.Pokemon2Repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
@Transactional
@Profile("local-mysql")
@RequiredArgsConstructor
public class Pokemon2DatabaseInitializer implements ApplicationRunner {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;

    List<String> moveKeys = List.of("id", "name", "nameAppend", "effect", "type", "defaultType", "category",
            "moveTarget", "power", "accuracy", "pp", "chance", "priority", "generation", "flags");
    List<String> pokemonKeys = List.of("speciesId", "name", "type1", "type2", "ability1", "ability2", "abilityHidden",
            "abilityPassive", "generation", "legendary", "subLegendary", "mythical", "getEvolutionLevels", "baseTotal",
            "baseStats", "height", "weight", "canChangeForm", "eggMoves", "moves", "biomes");

    private Scanner scanner;

    @Override
    public void run(ApplicationArguments args) {
        File file = new File("src/main/java/com/pokerogue/helper/global/config/pokemon.txt");
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            List<String> values = new ArrayList<>();
            StringTokenizer stringTokenizer = new StringTokenizer(line, "/");

            while (stringTokenizer.hasMoreTokens()) {
                values.add(stringTokenizer.nextToken().strip());
            }

            if (values.size() != pokemonKeys.size()) {
                values.add("EMPTY");
            }
            Map<String, String> map = IntStream.range(0, pokemonKeys.size())
                    .boxed()
                    .collect(Collectors.toMap(pokemonKeys::get, values::get));

            pokemon2Repository.save(map.get("name"), map);
        }
        saveMove();
    }

    private void saveMove() {
        File file = new File("src/main/java/com/pokerogue/helper/global/config/move.txt");

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
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
    }
}
