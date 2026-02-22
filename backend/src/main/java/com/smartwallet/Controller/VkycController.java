package com.smartwallet.Controller;

import com.smartwallet.model.Vkyc;
import com.smartwallet.service.VkycService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/vkyc")
public class VkycController {

    private final VkycService vkycService;

            public VkycController(VkycService vkycService) {
        this.vkycService = vkycService;
    }

    // 1️⃣ Start VKYC
    @PostMapping("/start/{userId}")
    public ResponseEntity<Vkyc> start(@PathVariable String userId) {
        return ResponseEntity.ok(vkycService.startVkyc(userId));
    }

    // 2️⃣ Upload REAL Video File
    @PostMapping("/upload/{userId}")
    public ResponseEntity<Vkyc> upload(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file
    ) {

        Vkyc vkyc = vkycService.uploadVideo(userId, file);

        return ResponseEntity.ok(vkyc);
    }

    // 3️⃣ Move To Review
    @PostMapping("/review/{userId}")
    public ResponseEntity<Vkyc> review(@PathVariable String userId) {
        return ResponseEntity.ok(vkycService.moveToReview(userId));
    }

    // 4️⃣ Approve
    @PostMapping("/approve/{userId}")
    public ResponseEntity<Vkyc> approve(@PathVariable String userId) {
        return ResponseEntity.ok(vkycService.approve(userId));
    }

    // 5️⃣ Reject
    @PostMapping("/reject/{userId}")
    public ResponseEntity<Vkyc> reject(
            @PathVariable String userId,
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(vkycService.reject(userId, reason));
    }

    // 6️⃣ Get VKYC Status
    @GetMapping("/{userId}")
    public ResponseEntity<Vkyc> get(@PathVariable String userId) {
        return ResponseEntity.ok(vkycService.getByUserId(userId));
    }
}