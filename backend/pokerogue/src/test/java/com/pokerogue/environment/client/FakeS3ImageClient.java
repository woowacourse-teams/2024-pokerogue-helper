package com.pokerogue.environment.client;

import com.pokerogue.external.s3.client.S3ImageClient;

public class FakeS3ImageClient extends S3ImageClient {

    public FakeS3ImageClient() {
        super("fake", "https://fake", "https://fake");
    }

    @Override
    public void putObject(byte[] imageBytes, String contentType, String fileName) {
    }

    @Override
    public String getFileUrl(String fileName) {
        return "dummy";
    }
}
