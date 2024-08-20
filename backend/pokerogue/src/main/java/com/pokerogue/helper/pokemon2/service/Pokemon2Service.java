package com.pokerogue.helper.pokemon2.service;


import com.pokerogue.helper.pokemon2.dto.Pokemon2Response;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.dto.PokemonDetailResponse;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Pokemon2Service {

    private final Pokemon2Repository pokemon2Repository;
    private final MoveRepository moveRepository;

//    private List<Pokemon2Response> findAllCache;
    private Map<String, PokemonDetailResponse> findByIdCache = new HashMap<>();

    public List<Pokemon2Response> findAll() {
        return initFindAllCache();
    }

    private List<Pokemon2Response> initFindAllCache() {
        return  pokemon2Repository.findAll().values().stream().map(Pokemon2Response::from).toList();
    }

//    private List<Map<Object, Object>> initPokemons() {
//        List<Map<Object, Object>> ret = new ArrayList<>();
//        Map<String, Map<String, String>> all = pokemon2Repository.findAll();
//        for (var entry : all.entrySet()) {
//            Map<Object, Object> cur = new HashMap<>();
//
//            String name = entry.getKey();
//            Map<String, String> value = entry.getValue();
//
//            cur.put("id", name);
//            cur.put("pokedexNumber", Integer.parseInt(value.get("speciesId")));
//            cur.put("name", name);
//            cur.put("image", "todo:imageLink");
//            List<PokemonTypeResponse> pokemonTypeResponses = List.of(
//                    new PokemonTypeResponse(value.get("type1"), "todo:logo"),
//                    new PokemonTypeResponse(value.get("type2"), "todo:logo")
//            );
//            cur.put("pokemonTypeResponse", pokemonTypeResponses);
//
//            cur.put("generation", Integer.parseInt(value.get("generation")));
//            cur.put("totalStats", Integer.parseInt(value.get("baseTotal")));
//
//            List<Integer> baseStats = Arrays.stream(value.get("baseStats").split(","))
//                    .mapToInt(Integer::parseInt)
//                    .boxed()
//                    .toList();
//            cur.put("hp", baseStats.get(0));
//            cur.put("attack", baseStats.get(1));
//            cur.put("defense", baseStats.get(2));
//            cur.put("specialAttack", baseStats.get(3));
//            cur.put("specialDefense", baseStats.get(4));
//            cur.put("speed", baseStats.get(5));
//
//            ret.add(cur);
//        }
//        ret.sort(Comparator.comparingInt(a -> (Integer) a.get("pokedexNumber")));
//        return ret;
//    }

    public PokemonDetailResponse findById(String id) {
        if (findByIdCache.isEmpty()) {
            initFindByIdCache();
            return findByIdCache.get(id);
        }
        return findByIdCache.get(id);
    }

    private void initFindByIdCache() {
//        Map<String, Map<String, String>> all = pokemon2Repository.findAll();

//        for (Entry<String, Map<String, String>> entry : all.entrySet()) {
//            Map<String, Object> tmp = new HashMap<>();
//        }
    }
}
