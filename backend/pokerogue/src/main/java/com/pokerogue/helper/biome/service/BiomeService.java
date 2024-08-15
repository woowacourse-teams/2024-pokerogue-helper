package com.pokerogue.helper.biome.service;

import com.pokerogue.helper.biome.domain.Biome;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiomeService {

    private final BiomeRepository biomeRepository;

    public List<BiomeResponse> findBiomes() {
        List<Biome> biomeResponses = biomeRepository.findAllBiomes();
        return null;
    }
}
