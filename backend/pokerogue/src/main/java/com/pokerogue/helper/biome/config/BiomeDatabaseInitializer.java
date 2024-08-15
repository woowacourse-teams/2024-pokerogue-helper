package com.pokerogue.helper.biome.config;

import com.pokerogue.helper.biome.repository.BiomeRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BiomeDatabaseInitializer implements ApplicationRunner {

    private final BiomeRepository biomeRepository;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome.txt")
        );
        while (true) {
            String biome = bufferedReader.readLine();
            if (biome == null) {
                break;
            }
            biomeRepository.saveBiome(biome);
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-links.txt")
        );
        while (true) {
            String biomeLink = bufferedReader.readLine();
            if (biomeLink == null) {
                break;
            }
            biomeRepository.saveBiomeLink(biomeLink);
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-trainer.txt")
        );
        while (true) {
            String biomeTrainer = bufferedReader.readLine();
            if (biomeTrainer == null) {
                break;
            }
            biomeRepository.saveBiomeTrainer(biomeTrainer);
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/trainer-species.txt")
        );
        while (true) {
            String trainerSpecie = bufferedReader.readLine();
            if (trainerSpecie == null) {
                break;
            }
            biomeRepository.saveTrainerSpecie(trainerSpecie);
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-type.txt")
        );
        while (true) {
            String biomeType = bufferedReader.readLine();
            if (biomeType == null) {
                break;
            }
            biomeRepository.saveBiomeType(biomeType);
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/trainer-type.txt")
        );
        while (true) {
            String trainerType = bufferedReader.readLine();
            if (trainerType == null) {
                break;
            }
            biomeRepository.saveTrainerType(trainerType);
        }
    }
}
