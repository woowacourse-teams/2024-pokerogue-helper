package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.data.Ability;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryAbilityRepository {

    private final Map<String, Ability> abilities;

    public InMemoryAbilityRepository() {
        this.abilities = new HashMap<>();
    }

    public void save(Ability ability) {
        abilities.put(ability.getId(), ability);
    }

    public List<Ability> findAll() {
        return abilities.values().stream()
                .toList();
    }

    public Optional<Ability> findById(String id) {
        return Optional.ofNullable(abilities.get(id));
    }
}
