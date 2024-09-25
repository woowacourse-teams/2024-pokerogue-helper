package com.pokerogue.helper.battle;

import com.pokerogue.helper.battle.data.InMemoryTypeMatching;
import com.pokerogue.helper.type.data.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTypeMatchingRepository {

    private final Map<Integer, InMemoryTypeMatching> typeMatchings = new HashMap<>();

    public void save(InMemoryTypeMatching inMemoryTypeMatching) {
        int id = Objects.hash(inMemoryTypeMatching.fromType(), inMemoryTypeMatching.toType());
        typeMatchings.put(id, inMemoryTypeMatching);
    }

    public List<InMemoryTypeMatching> findAll() {
        return typeMatchings.values()
                .stream()
                .toList();
    }

    public Optional<InMemoryTypeMatching> findByFromTypeAndToType(Type fromType, Type toType) {
        int id = Objects.hash(fromType, toType);
        return Optional.ofNullable(typeMatchings.get(id));
    }
}
