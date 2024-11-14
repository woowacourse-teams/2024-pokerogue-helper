package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.data.Biome;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BiomeRepository extends MongoRepository<Biome, String> {
}
