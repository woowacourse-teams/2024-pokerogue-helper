package com.pokerogue.helper.ability2.repository;

import com.pokerogue.helper.ability2.data.Ability;
import com.pokerogue.helper.biome.data.Biome;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AbilityRepository {

    private final Map<String, Ability> abilities;

    public AbilityRepository() {
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
