package com.dishdash.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadImage(InputStream fileInputStream, String fileName, String fileType) {
        try {
            int contentLength = fileInputStream.available();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(fileType);
            metadata.setContentLength(contentLength);

            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, fileInputStream, metadata);
            amazonS3.putObject(request);
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (AmazonServiceException e) {
            throw new RuntimeException("Erro ao enviar imagem para o S3", e);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao calcular o tamanho do arquivo", e);
        }
    }

}
