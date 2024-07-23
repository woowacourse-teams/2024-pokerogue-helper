package com.pokerogue.helper.type.service;


import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonTypeService {

    private final PokemonTypeRepository pokemonTypeRepository;

    public List<PokemonTypeResponse> findTypes() {
        List<PokemonType> pokemonTypes = pokemonTypeRepository.findAll();

        return pokemonTypes.stream()
                .map(PokemonTypeResponse::from)
                .toList();
    }
}
