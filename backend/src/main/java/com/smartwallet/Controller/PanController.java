package com.smartwallet.Controller;

import com.smartwallet.dto.PanDto;
import com.smartwallet.service.PanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pan-screen")
public class PanController {

    private final PanService panService;

    public PanController(PanService panService) {
        this.panService = panService;
    }

    @PostMapping("/details")
    public ResponseEntity<String> panDetails(@RequestBody PanDto request) {

        panService.submitPan(
                request.getPanName(),
                request.getPanNumber(),
                request.getDob()
        );

        return ResponseEntity.ok("PAN submitted successfully");
    }
}
