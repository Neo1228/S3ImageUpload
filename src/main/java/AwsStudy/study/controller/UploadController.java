package AwsStudy.study.controller;

import AwsStudy.study.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String fileUrl = s3Uploader.uploadFile(file, "images");
            model.addAttribute("message", "File uploaded successfully!");
            model.addAttribute("fileUrl", fileUrl);
        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }
        return "home"; // 업로드 완료 후 다시 home.html 렌더링
    }
}
