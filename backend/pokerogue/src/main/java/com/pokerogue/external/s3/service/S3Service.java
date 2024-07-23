package com.pokerogue.external.s3.service;

import com.pokerogue.external.s3.client.S3ImageClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String POKEAPI_IMAGE_CONTENT_TYPE = "image/png";

    private final S3ImageClient s3ImageClient;

    public String postImageToS3(byte[] imageBytes) {
        String key = makeRandomFileName();
        s3ImageClient.putObject(imageBytes, POKEAPI_IMAGE_CONTENT_TYPE, key);

        return s3ImageClient.getFileUrl(key);
    }

    private String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }
}
