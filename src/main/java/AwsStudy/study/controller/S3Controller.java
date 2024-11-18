package AwsStudy.study.controller;

import AwsStudy.study.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class S3Controller {

    private final S3Uploader s3Uploader;

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload"; // templates/upload.html로 매핑
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String url = s3Uploader.uploadFile(file, "images"); // S3에 파일 업로드
            model.addAttribute("url", url); // 업로드된 파일 URL을 모델에 추가
            return "result"; // templates/result.html로 매핑
        } catch (IOException e) {
            model.addAttribute("error", "파일 업로드 실패: " + e.getMessage());
            return "upload"; // 실패 시 다시 업로드 페이지로
        }
    }
}
