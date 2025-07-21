package com.example.demo.controller;

import com.example.demo.service.ExportCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PdfController {
    private final ExportCartService exportCartService;
    @GetMapping("/export-pdf")
    public ResponseEntity<InputStreamResource> exportPdf(@RequestParam UUID cartId,
                                                         @AuthenticationPrincipal UserDetails user) {

        ByteArrayInputStream bis = exportCartService.exportPatientRecordToPdf(cartId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=users.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
