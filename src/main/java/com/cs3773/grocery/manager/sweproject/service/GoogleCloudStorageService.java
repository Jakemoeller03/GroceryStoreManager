package com.cs3773.grocery.manager.sweproject.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {

    @Value("${gcs.bucket.name}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String credentialsPath;

    private final Storage storage;

    public GoogleCloudStorageService(@Value("${gcs.bucket.name}") String bucketName,
                                     @Value("${spring.cloud.gcp.storage.credentials.location}") String credentialsPath) {
        this.bucketName = bucketName;
        this.credentialsPath = credentialsPath;
        this.storage = createStorageService();
    }

    private Storage createStorageService() {
        try {
            // Remove "file:" prefix if present
            String cleanPath = credentialsPath.replace("file:", "");

            System.out.println("Loading GCS credentials from: " + cleanPath);

            // Load credentials with explicit storage scope
            ServiceAccountCredentials credentials = (ServiceAccountCredentials) ServiceAccountCredentials
                    .fromStream(new FileInputStream(cleanPath))
                    .createScoped(Collections.singleton("https://www.googleapis.com/auth/cloud-platform"));

            System.out.println("Loaded credentials for service account: " + credentials.getClientEmail());
            System.out.println("Project ID: " + credentials.getProjectId());

            return StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .setProjectId("cs3773project") // Explicitly set your project ID
                    .build()
                    .getService();
        } catch (IOException e) {
            System.err.println("Failed to load GCS credentials from " + credentialsPath + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Google Cloud Storage", e);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        System.out.println("Attempting to upload file: " + file.getOriginalFilename() + " to bucket: " + bucketName);

        // Generate unique filename
        String fileName = generateFileName(file.getOriginalFilename());

        // Create blob info
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        try {
            // Upload file
            Blob blob = storage.create(blobInfo, file.getBytes());

            System.out.println("Successfully uploaded file: " + fileName);

            // Return the public URL since your bucket has public access
            return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
        } catch (Exception e) {
            System.err.println("Failed to upload file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            return storage.delete(blobId);
        } catch (Exception e) {
            System.err.println("Failed to delete file: " + fileName + " - " + e.getMessage());
            return false;
        }
    }

    private String generateFileName(String originalFileName) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    public String extractFileNameFromUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.contains("/")) {
            return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        }
        return null;
    }
}