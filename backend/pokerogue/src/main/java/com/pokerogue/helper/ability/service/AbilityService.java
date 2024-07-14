package com.pokerogue.helper.ability.service;

import com.pokerogue.helper.ability.domain.Ability;
import com.pokerogue.helper.ability.dto.AbilityResponse;
import com.pokerogue.helper.ability.repository.AbilityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbilityService {

    private final AbilityRepository abilityRepository;

    public List<AbilityResponse> findAbilities() {
        List<Ability> abilities = abilityRepository.findAll();

        return abilities.stream()
                .map(AbilityResponse::from)
                .toList();
    }
}
