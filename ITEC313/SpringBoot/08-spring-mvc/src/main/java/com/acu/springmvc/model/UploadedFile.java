package com.acu.springmvc.model;

import java.time.LocalDateTime;

public class UploadedFile {
    
    private String originalFilename;
    private String storedFilename;
    private long size;
    private String contentType;
    private LocalDateTime uploadedAt;
    
    // Default constructor
    public UploadedFile() {
        this.uploadedAt = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public UploadedFile(String originalFilename, String storedFilename, long size, String contentType) {
        this();
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.size = size;
        this.contentType = contentType;
    }
    
    // Getters and Setters
    public String getOriginalFilename() {
        return originalFilename;
    }
    
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    
    public String getStoredFilename() {
        return storedFilename;
    }
    
    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
    
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    
    public String getFormattedSize() {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0);
        } else {
            return String.format("%.1f MB", size / (1024.0 * 1024.0));
        }
    }
    
    @Override
    public String toString() {
        return "UploadedFile{" +
                "originalFilename='" + originalFilename + '\'' +
                ", storedFilename='" + storedFilename + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
