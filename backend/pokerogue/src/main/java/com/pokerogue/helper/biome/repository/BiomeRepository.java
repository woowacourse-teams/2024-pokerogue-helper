package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.global.config.LocaleContextHolder;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BiomeRepository {

    private final BiomeMongoRepository biomeMongoRepository;

    public List<Biome> findAll() {
        return biomeMongoRepository.findAllByLanguage(LocaleContextHolder.getCurrentLocale());
    }

    public Optional<Biome> findByIndex(String index) {
        return biomeMongoRepository.findByIndexAndLanguage(index, LocaleContextHolder.getCurrentLocale());
    }
}
