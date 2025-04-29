package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.data.Biome;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BiomeRepository extends MongoRepository<Biome, String> {

    Optional<Biome> findByIdAndLanguage(String id, String language);
}
