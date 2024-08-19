package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class TypeMatchingRepository {

    private final Map<Integer, TypeMatching> typeMatchings = new HashMap<>();

    public void save(TypeMatching typeMatching) {
        int id = Objects.hash(typeMatching.fromType(), typeMatching.toType());
        typeMatchings.put(id, typeMatching);
    }

    public List<TypeMatching> findAll() {
        return typeMatchings.values()
                .stream()
                .toList();
    }
}
