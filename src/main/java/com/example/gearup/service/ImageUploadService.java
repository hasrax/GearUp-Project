package com.example.gearup.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageUploadService
{
    private final String uploadDir = "uploads/"; // Base directory for uploads

    /**
     * Uploads an image file to the specified sub-directory.
     *
     * @param file         The image file to upload.
     * @param subDirectory The sub-directory within the upload directory (e.g., "users").
     * @return The relative path to the uploaded image.
     * @throws IOException If an I/O error occurs.
     */
    public String uploadImage(MultipartFile file, String subDirectory) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }
        // Validate file size (e.g., max 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IOException("File size exceeds limit");
        }
        // Create sub-directory path
        Path subDirPath = Paths.get(uploadDir, subDirectory);
        if (!Files.exists(subDirPath)) {
            Files.createDirectories(subDirPath);
        }

        // Generate a unique file name
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // Define the target path
        Path targetPath = subDirPath.resolve(fileName);

        // Save the file to the target location
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative path (for accessing the image via URL)
        return "/" + uploadDir + subDirectory + "/" + fileName;
    }

    /**
     * Deletes an image file from the specified path.
     *
     * @param imagePath The relative path to the image file.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return false;
        }

        Path path = Paths.get("." + imagePath); // Assuming 'uploads/' is in the project root
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            // Log the exception (not shown here)
            return false;
        }
    }

    /**
     * Extracts the file extension from the original file name.
     *
     * @param fileName The original file name.
     * @return The file extension (including the dot), or an empty string if none.
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}
