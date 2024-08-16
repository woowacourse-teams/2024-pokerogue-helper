package com.pokerogue.helper.pokemon2;


import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Pokemon2Service {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;

    private List<Map<Object, Object>> findAllCache = new ArrayList<>();
    private Map<String, Object> findByIdCache = new HashMap<>();

    public List<Map<Object, Object>> findAll() {
        if (findAllCache.isEmpty()) {
            findAllCache = initPokemons();
        }
        return findAllCache;
    }

    private List<Map<Object, Object>> initPokemons() {
        List<Map<Object, Object>> ret = new ArrayList<>();
        Map<String, Map<String, String>> all = pokemon2Repository.findAll();
        for (var entry : all.entrySet()) {
            Map<Object, Object> cur = new HashMap<>();

            String name = entry.getKey();
            Map<String, String> value = entry.getValue();

            cur.put("id", name);
            cur.put("pokedexNumber", Integer.parseInt(value.get("speciesId")));
            cur.put("name", name);
            cur.put("image", "todo:imageLink");
            List<PokemonTypeResponse> pokemonTypeResponses = List.of(
                    new PokemonTypeResponse(value.get("type1"), "todo:logo"),
                    new PokemonTypeResponse(value.get("type2"), "todo:logo")
            );
            cur.put("pokemonTypeResponse", pokemonTypeResponses);

            cur.put("generation", Integer.parseInt(value.get("generation")));
            cur.put("totalStats", Integer.parseInt(value.get("baseTotal")));

            List<Integer> baseStats = Arrays.stream(value.get("baseStats").split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toList();
            cur.put("hp", baseStats.get(0));
            cur.put("attack", baseStats.get(1));
            cur.put("defense", baseStats.get(2));
            cur.put("specialAttack", baseStats.get(3));
            cur.put("specialDefense", baseStats.get(4));
            cur.put("speed", baseStats.get(5));

            ret.add(cur);
        }
        ret.sort(Comparator.comparingInt(a -> (Integer) a.get("pokedexNumber")));
        return ret;
    }

    public Map<Object, Object> findById(String id) {
        if (findByIdCache.isEmpty()) {
            initFindByIdCache();
            return (Map) findByIdCache.get(id);
        }
        return (Map) findByIdCache.get(id);
    }

    private void initFindByIdCache() {
        Map<String, Map<String, String>> all = pokemon2Repository.findAll();

        for (Entry<String, Map<String, String>> entry : all.entrySet()) {
            Map<String, Object> tmp = new HashMap<>();

            String name = entry.getKey();
            Map<String, String> value = entry.getValue();

            tmp.put("id", name);
            tmp.put("pokeDexNumber", Integer.parseInt(value.get("speciesId")));
            tmp.put("name", value.get("name"));
            tmp.put("pokemonImage", "dummy-image");

            String ability1 = value.get("ability1");
            String ability2 = value.get("ability2");
            String abilityPassive = value.get("abilityPassive");
            String abilityHidden = value.get("abilityHidden");

            tmp.put("pokemonAbilityResponses", List.of(
                    new PokemonAbilityResponse(abilityPassive, abilityPassive, "description", true, false),
                    new PokemonAbilityResponse(ability1, ability1, "description", false, false),
                    new PokemonAbilityResponse(ability2, ability2, "description", false, false),
                    new PokemonAbilityResponse(abilityHidden, abilityHidden, "description", false, true)
            ));

            String type1 = value.get("type1");
            String type2 = value.get("type2");
            tmp.put("pokemonTypeResponses", List.of(
                    new PokemonTypeResponse(type1, "dummy-logo")
                    , new PokemonTypeResponse(type2, "dummy-logo")
            ));

            tmp.put("totalStats", Integer.parseInt(value.get("baseTotal")));
            String[] baseStats = value.get("baseStats").split(",");
            tmp.put("hp", Integer.parseInt(baseStats[0]));
            tmp.put("attack", Integer.parseInt(baseStats[1]));
            tmp.put("defense", Integer.parseInt(baseStats[2]));
            tmp.put("specialAttack", Integer.parseInt(baseStats[3]));
            tmp.put("specialDefense", Integer.parseInt(baseStats[4]));
            tmp.put("speed", Integer.parseInt(baseStats[5]));
            tmp.put("legendary", Boolean.valueOf(value.get("legendary")));
            tmp.put("subLegendary", Boolean.valueOf(value.get("subLegendary")));
            tmp.put("mythical", Boolean.valueOf(value.get("mythical")));
            tmp.put("canChangeForm", Boolean.valueOf(value.get("canChangeForm")));
            tmp.put("weight", Double.valueOf(value.get("weight")));
            tmp.put("height", Double.valueOf(value.get("height")));

            String[] evolutionLevels = value.get("getEvolutionLevels").split(",");
            var evolutions = new ArrayList<>();
            if (evolutionLevels.length % 2 == 0) {
                for (int i = 0; i < evolutionLevels.length; i += 2) {
                    String evovleName = evolutionLevels[i];
                    Integer level = Integer.parseInt(evolutionLevels[i + 1]);
                    evolutions.add(new EvolutionResponse(evovleName, level));
                }
                tmp.put("evolutions", evolutions);
            }
            String[] moves = value.get("moves").split(",");

            List<String> eggMoves = Arrays.stream(value.get("eggMoves").split(",")).toList();
            List<String> biomes = Arrays.stream(value.get("biomes").split(",")).toList();

            tmp.put("eggMoves", eggMoves.stream()
                    .map(r -> new EggMoveResponse(r, r))
                    .toList()
            );

            tmp.put("biomes", biomes.stream()
                    .map(r -> new BiomeResponse(r, r))
                    .toList()
            );
            var moveAll = moveRepository.findAll();
            List<MoveResponse> moveResponses = new ArrayList<>();

            for (int i = 0; i < moves.length; i += 2) {
                String moveName = moves[i].strip();
                Integer moveLevel = Integer.parseInt(moves[i + 1]);
                var moveValues = moveAll.get(moveName);
                Integer power = Integer.parseInt(moveValues.get("power"));
                Integer accuracy = Integer.parseInt(moveValues.get("accuracy"));
                String type = moveValues.get("type");
                String category = moveValues.get("category");
                moveResponses.add(new MoveResponse(moveName, moveLevel, power, accuracy, type, category));
            }
            tmp.put("moves", moveResponses);
            findByIdCache.put(name, tmp);
        }
    }
}
