package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.global.config.LocaleContextHolder;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AbilityRepository {

    private final AbilityMongoRepository abilityMongoRepository;

    public List<Ability> findAll() {
        return abilityMongoRepository.findAllByLanguage(LocaleContextHolder.getCurrentLocale());
    }

    public Optional<Ability> findByIndex(String index) {
        return abilityMongoRepository.findByIndexAndLanguage(index, LocaleContextHolder.getCurrentLocale());
    }
}
