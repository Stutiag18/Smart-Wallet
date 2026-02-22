package com.smartwallet.service;

import com.smartwallet.enums.VkycStatus;
import com.smartwallet.model.Vkyc;
import com.smartwallet.repository.VkycRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VkycService {

    private final VkycRepository vkycRepository;

    // Folder where videos will be stored
    private static final String UPLOAD_DIR = "uploads/";

    public VkycService(VkycRepository vkycRepository) {
        this.vkycRepository = vkycRepository;
    }

    // 1️⃣ Start VKYC
    public Vkyc startVkyc(String userId) {

        if (vkycRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("VKYC already exists for this user");
        }

        Vkyc vkyc = new Vkyc();
        vkyc.setUserId(userId);
        vkyc.setStatus(VkycStatus.NOT_STARTED);

        return vkycRepository.save(vkyc);
    }

    // 2️⃣ Upload Video File
    public Vkyc uploadVideo(String userId, MultipartFile file) {

        Vkyc vkyc = vkycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("VKYC not found"));

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Limit file size to 20MB
        if (file.getSize() > 20 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 20MB limit");
        }

        // Validate file type
        if (file.getContentType() == null ||
                !file.getContentType().startsWith("video")) {
            throw new RuntimeException("Only video files are allowed");
        }

        // Create upload directory if not exists
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique file name
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = UPLOAD_DIR + uniqueFileName;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save video file");
        }

        // Update Mongo record
        vkyc.setVideoFileName(uniqueFileName);
        vkyc.setVideoPath(filePath);
        vkyc.setStatus(VkycStatus.VIDEO_UPLOADED);

        return vkycRepository.save(vkyc);
    }

    // 3️⃣ Move To Review
    public Vkyc moveToReview(String userId) {

        Vkyc vkyc = vkycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("VKYC not found"));

        vkyc.setStatus(VkycStatus.UNDER_REVIEW);

        return vkycRepository.save(vkyc);
    }

    // 4️⃣ Approve VKYC
    public Vkyc approve(String userId) {

        Vkyc vkyc = vkycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("VKYC not found"));

        vkyc.setStatus(VkycStatus.APPROVED);
        vkyc.setReviewedAt(LocalDateTime.now());

        return vkycRepository.save(vkyc);
    }

    // 5️⃣ Reject VKYC
    public Vkyc reject(String userId, String reason) {

        Vkyc vkyc = vkycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("VKYC not found"));

        vkyc.setStatus(VkycStatus.REJECTED);
        vkyc.setRejectionReason(reason);
        vkyc.setReviewedAt(LocalDateTime.now());

        return vkycRepository.save(vkyc);
    }

    // 6️⃣ Get VKYC by userId
    public Vkyc getByUserId(String userId) {

        return vkycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("VKYC not found"));
    }
}