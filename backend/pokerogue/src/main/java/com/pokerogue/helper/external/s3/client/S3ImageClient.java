package com.pokerogue.helper.external.s3.client;

import com.pokerogue.helper.external.s3.domain.S3TagMaker;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3ImageClient {

    private final S3Client s3Client;
    private final String bucket;
    private final String endPoint;
    private final String cloudfrontEndPoint;

    public S3ImageClient(@Value("${cloud.aws.s3.bucket}") String bucket,
                         @Value("${cloud.aws.s3.endpoint}") String endPoint,
                         @Value("${cloud.aws.cloudfront.endpoint}") String cloudfrontEndPoint) {
        this.s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(InstanceProfileCredentialsProvider.create())
                .endpointOverride(URI.create(endPoint))
                .forcePathStyle(true)
                .build();
        this.bucket = bucket;
        this.endPoint = endPoint;
        this.cloudfrontEndPoint = cloudfrontEndPoint;
    }

    public void putObject(byte[] imageBytes, String contentType, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .key(fileName)
                .bucket(bucket)
                .tagging(S3TagMaker.makeDefaultTags())
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));
    }

    public String getFileUrl(String fileName) {
        S3Utilities s3Utilities = s3Client.utilities();
        GetUrlRequest urlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        String originUrl = s3Utilities.getUrl(urlRequest).toString();

        return originUrl.replace(endPoint, cloudfrontEndPoint);
    }
}
