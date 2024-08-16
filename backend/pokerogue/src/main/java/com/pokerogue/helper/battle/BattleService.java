package com.pokerogue.helper.battle;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final WeatherRepository weatherRepository;

    public List<WeatherResponse> findWeathers() {
        return weatherRepository.findAll().stream()
                .map(WeatherResponse::from)
                .toList();
    }
}
