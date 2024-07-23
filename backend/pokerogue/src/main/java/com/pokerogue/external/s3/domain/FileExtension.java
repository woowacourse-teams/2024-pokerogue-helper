package com.pokerogue.external.s3.domain;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileExtension {

    JPG(".jpg"),
    JPEG(".jpeg"),
    PNG(".png"),
    GIF(".gif"),
    SVG(".svg");

    private final String name;

    public static String findExtensionName(String name) {
        FileExtension foundExtension = Arrays.stream(values())
                .filter(fileExtension -> isSameFileExtension(name, fileExtension))
                .findAny()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.FILE_EXTENSION_NOT_APPLY));

        return foundExtension.getName();
    }

    private static boolean isSameFileExtension(String name, FileExtension fileExtension) {
        return fileExtension.name.equalsIgnoreCase(name);
    }
}
