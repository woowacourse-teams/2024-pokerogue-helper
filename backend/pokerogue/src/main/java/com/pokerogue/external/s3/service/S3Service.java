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
    private static final String POKEROGUE_TYPE_IMAGE_FOLDER = "pokerogue/type/";
    private static final String POKEROGUE_MOVE_CATEGORY_IMAGE_FOLDER = "pokerogue/move-category/";
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

    private String makeRandomFileName() {
        return POKEMON_IMAGE_FOLDER + UUID.randomUUID();
    }

    private String makeTypeFileName(String typeName) {
        return TYPE_IMAGE_FOLDER + typeName + SVG_EXTENSION;
    }

    public String getPokerogueTypeImageFromS3(String typeName) {
        String key = POKEROGUE_TYPE_IMAGE_FOLDER + typeName + "-1" + PNG_EXTENSION;
        return s3ImageClient.getFileUrl(key);
    }
}
