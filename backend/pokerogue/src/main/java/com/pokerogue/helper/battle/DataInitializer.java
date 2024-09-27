package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private static final int FIRST_LINE_NUMBER = 3;
    private static final String FIELD_DELIMITER = "/";
    private static final String LIST_DELIMITER = ",";

    private final BattleMoveRepository battleMoveRepository;
    private final InMemoryTypeMatchingRepository typeMatchingRepository;

    @Override
    public void run(ApplicationArguments args) {
        saveData("data/battle/battle-move.txt", fields -> {
            BattleMove battleMove = createMove(fields);
            battleMoveRepository.save(battleMove);
        });
        saveData("data/battle/type-matching.txt", fields -> {
            TypeMatching typeMatching = createTypeMatching(fields);
            typeMatchingRepository.save(typeMatching);
        });
    }

    private void saveData(String path, Consumer<List<String>> createAndSaveOperation) {
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
                createAndSaveOperation.accept(fields);
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getMessage(), e);
        }
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

    private BattleMove createMove(List<String> fields) {
        Type moveType = Type.findByName(fields.get(4))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        MoveCategory moveCategory = MoveCategory.findByEngName(fields.get(6))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));

        return new BattleMove(
                fields.get(0),
                fields.get(1),
                fields.get(2),
                fields.get(3),
                moveType,
                fields.get(5),
                moveCategory,
                fields.get(7),
                convertToInteger(fields.get(8)),
                convertToInteger(fields.get(9)),
                convertToInteger(fields.get(10)),
                convertToInteger(fields.get(11)),
                convertToInteger(fields.get(12)),
                convertToInteger(fields.get(13)),
                fields.get(14)
        );
    }

    private TypeMatching createTypeMatching(List<String> fields) {
        Type fromType = Type.findByEngName(fields.get(0))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        Type toType = Type.findByEngName(fields.get(1))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        double result = convertToDouble(fields.get(2));

        return new TypeMatching(fromType, toType, result);
    }

    private double convertToDouble(String data) {
        try {
            return Double.parseDouble(data);
        } catch (NumberFormatException e) {
            throw new GlobalCustomException(ErrorMessage.PARSE_ERROR);
        }
    }

    private Integer convertToInteger(String data) {
        try {
            return Integer.valueOf(data);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
