package com.pokerogue.external.s3.service;

import com.pokerogue.external.s3.client.S3ImageClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String POKEMON_IMAGE_FOLDER = "image/";
    private static final String TYPE_IMAGE_FOLDER = "type/";
    private static final String BIOME_IMAGE_FOLDER = "pokerogue/biome/";
    private static final String TRAINER_IMAGE_FOLDER = "pokerogue/trainer/trainer/";
    private static final String SVG_EXTENSION = ".svg";
    private static final String PNG_EXTENSION = ".png";

    private final S3ImageClient s3ImageClient;

    public String postImageToS3(byte[] imageBytes) {
        String key = makeRandomFileName();
        s3ImageClient.putObject(imageBytes, MediaType.IMAGE_PNG_VALUE, key);

        return s3ImageClient.getFileUrl(key);
    }

    public String getTypeImageFromS3(String typeName) {
        String key = makeTypeFileName(typeName);
        return s3ImageClient.getFileUrl(key);
    }

    public String getBiomeImageFromS3(String biomeId) {
        String key = makeBiomeFileName(biomeId);
        return s3ImageClient.getFileUrl(key);
    }

    private String makeBiomeFileName(String biomeId) {
        return BIOME_IMAGE_FOLDER + biomeId + PNG_EXTENSION;
    }

    public String getTrainerImageFromS3(String trainerId) {
        String key = makeTrainerFileName(trainerId);
        return s3ImageClient.getFileUrl(key);
    }

    private String makeTrainerFileName(String trainerId) {
        return TRAINER_IMAGE_FOLDER + trainerId + PNG_EXTENSION;
    }

    private String makeRandomFileName() {
        return POKEMON_IMAGE_FOLDER + UUID.randomUUID();
    }

    private String makeTypeFileName(String typeName) {
        return TYPE_IMAGE_FOLDER + typeName + SVG_EXTENSION;
    }
}
