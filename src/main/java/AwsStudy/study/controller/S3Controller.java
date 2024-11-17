package AwsStudy.study.controller;

import AwsStudy.study.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String url = s3Uploader.uploadFile(file, "images"); // 디렉토리 이름 설정
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        }
    }
}
