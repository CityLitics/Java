package school.sptech.service;

import school.sptech.config.S3Config;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import java.io.InputStream;

public class S3Service {
    private final S3Client s3 = S3Config.getS3Client();
    private final String bucketNome = "bucket-leitura-java-039048919469-us-east-1-an";

    public InputStream obterArquivo(String chaveS3) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketNome)
                .key(chaveS3)
                .build();
        return s3.getObject(request);
    }
}