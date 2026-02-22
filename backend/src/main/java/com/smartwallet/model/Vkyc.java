package com.smartwallet.model;

import com.smartwallet.enums.VkycStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vkyc_records")
public class Vkyc {

    @Id
    private String id;

    private String userId;

    private String videoFileName;
    private String videoPath;

    private Long fileSize;
    private String mimeType;

    private VkycStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;

    private String reviewedBy;
    private String rejectionReason;

    public Vkyc() {
        this.createdAt = LocalDateTime.now();
        this.status = VkycStatus.NOT_STARTED;
    }

    // ===== Getters & Setters =====

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public VkycStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setStatus(VkycStatus status) {
        this.status = status;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}