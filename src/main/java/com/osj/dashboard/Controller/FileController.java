package com.osj.dashboard.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileController implements WebMvcConfigurer {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @PostMapping("/tui/image")
    public String uploadEditorImage(@RequestParam("image") final MultipartFile image) {
        if (image.isEmpty()) {
            return "";
        }

        try {
            String orginFilename = image.getOriginalFilename();                                         // 원본 파일명
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
            String extension = orginFilename.substring(orginFilename.lastIndexOf(".") + 1);  // 확장자
            String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
            String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로

            // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                // 파일 저장 (write to disk)
                File uploadFile = new File(fileFullPath);
                image.transferTo(uploadFile);
                return saveFilename;

            } catch (IOException e) {
                throw new RuntimeException("Failed to write image file in disk.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image file.");
        }
    }

    @GetMapping(value = "/tui/image", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] renderEditorImage(@RequestParam("filename") final String filename) {
        // 업로드된 파일의 전체 경로
        String fileFullPath = Paths.get(uploadDir, filename).toString();

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (!uploadedFile.exists()) {
            throw new RuntimeException("File not found : " + filename);
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            return Files.readAllBytes(uploadedFile.toPath());

        } catch (IOException e) {
            throw new RuntimeException("Failed to render image file.");
        }
    }
}
