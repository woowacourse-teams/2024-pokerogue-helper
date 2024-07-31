package com.pokerogue.external.environ.client;

import com.pokerogue.external.s3.service.S3Service;

public class FakeS3Service extends S3Service {

    public FakeS3Service() {
        super(null);
    }

    @Override
    public String postImageToS3(byte[] imageBytes) {
        return "null";
    }

    @Override
    public String getTypeImageFromS3(String typeName) {
        return "null";
    }
}
