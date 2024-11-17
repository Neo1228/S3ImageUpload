package AwsStudy.study.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String dirName) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fileUrl = uploadToS3(convertedFile, fileName);
        deleteLocalFile(convertedFile); // 로컬에 임시 파일 삭제
        return fileUrl;
    }

    private String uploadToS3(File file, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // 파일을 퍼블릭으로 설정
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    private void deleteLocalFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
