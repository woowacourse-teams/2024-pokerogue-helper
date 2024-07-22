package com.pokerogue.helper.external.s3.service;

import com.pokerogue.helper.external.s3.client.S3ImageClient;
import com.pokerogue.helper.external.s3.domain.FileExtension;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String EXTENSION_STANDARD = ".";

    private final S3ImageClient s3ImageClient;

    public String postImageToS3(MultipartFile image) {
        String key = makeRandomFileName(image.getOriginalFilename());

        try {
            s3ImageClient.putObject(image.getBytes(), image.getContentType(), key);
        } catch (IOException e) {
            throw new GlobalCustomException(ErrorMessage.FILE_ACCESS_FAILED);
        }

        return s3ImageClient.getFileUrl(key);
    }

    private String makeRandomFileName(String fileName) {
        return UUID.randomUUID().toString().concat(findFileExtensionName(fileName));
    }

    private String findFileExtensionName(String fileName) {
        return FileExtension.findExtensionName(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(EXTENSION_STANDARD));
    }
}
