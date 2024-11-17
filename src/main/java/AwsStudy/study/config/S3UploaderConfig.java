package AwsStudy.study.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3UploaderConfig {

    @Value("${cloud.aws.credentials.access-key}") // application.yml에서 값 읽기
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}") // application.yml에서 값 읽기
    private String secretKey;

    @Value("${cloud.aws.region.static}") // application.yml에서 값 읽기
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
