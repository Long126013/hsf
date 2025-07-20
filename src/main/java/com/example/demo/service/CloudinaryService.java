package com.example.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {
    private final Cloudinary cloudinary;
    @Value("${cloudinary.base_folder}")
    private String baseFolder;

    private static final long MAX_FILE_SIZE = 1024 * 1024; // 1MB
    private static final int MIN_WIDTH = 50;
    private static final int MIN_HEIGHT = 50;

    public String uploadAvatar(MultipartFile avatarFile) throws IOException {
        validateAvatarFile(avatarFile);
        String publicId = baseFolder + "/avatars/" + UUID.randomUUID();
        Transformation transformation = new Transformation().width(150).height(150).gravity("face").crop("fill");
        Map<String, Object> uploadOptions = ObjectUtils.asMap(
                "public_id", publicId, "transformation", transformation, "resource_type", "image");
        try {
            Map uploadResult = cloudinary.uploader().upload(avatarFile.getBytes(), uploadOptions);
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            log.error("Error uploading file to Cloudinary: {}", e.getMessage());
            throw new RuntimeException("Error uploading file to Cloudinary.", e);
        }
    }

    private void validateAvatarFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("Vui lòng chọn một file để upload.");
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("Kích thước file không được vượt quá 1MB.");
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) throw new IllegalArgumentException("File upload phải là một file ảnh.");
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null) throw new IllegalArgumentException("Không thể đọc được file ảnh. File có thể bị hỏng.");
        if (bufferedImage.getWidth() < MIN_WIDTH || bufferedImage.getHeight() < MIN_HEIGHT)
            throw new IllegalArgumentException(String.format("Kích thước ảnh tối thiểu phải là %dx%d pixels.", MIN_WIDTH, MIN_HEIGHT));
    }
}