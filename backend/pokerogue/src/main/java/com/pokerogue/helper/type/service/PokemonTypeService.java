package com.pokerogue.helper.type.service;


import com.pokerogue.helper.type.collection.TypeMatching;
import com.pokerogue.helper.type.data.Type;
import com.pokerogue.helper.type.dto.PokemonTypeMatchingResponse;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import com.pokerogue.helper.type.dto.TypeMatchingResponse;
import com.pokerogue.helper.type.repository.TypeMatchingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonTypeService {

    private final TypeMatchingRepository typeMatchingRepository;

    public List<PokemonTypeResponse> findTypes() {
        List<Type> types = List.of(Type.values());

        return types.stream()
                .map(PokemonTypeResponse::from)
                .toList();
    }

    public PokemonTypeMatchingResponse findMatchingAndTypes() {
        List<TypeMatching> typeMatchings = typeMatchingRepository.findAll();
        List<TypeMatchingResponse> typeMatchingResponses = typeMatchings.stream()
                .map(TypeMatchingResponse::from)
                .toList();

        return new PokemonTypeMatchingResponse(typeMatchingResponses, findTypes());
    }
}
