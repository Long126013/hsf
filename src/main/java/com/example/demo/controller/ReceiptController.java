package com.example.demo.controller;

import com.example.demo.model.Receipt;
import com.example.demo.service.ExportReceiptService;
import com.example.demo.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@Controller
@RequestMapping("/receipt")
@RequiredArgsConstructor
public class ReceiptController {
    private final ExportReceiptService exportReceiptService;
    private final ReceiptService receiptService;

    @GetMapping("/{id}")
    public String viewReceipt(@PathVariable("id") UUID receiptId, Model model) {
        Receipt receipt = receiptService.getReceiptById(receiptId);
        if (receipt == null) {
            model.addAttribute("error", "Không tìm thấy hóa đơn!");
            return "receipt";
        }
        model.addAttribute("receipt", receipt);
        return "receipt";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> downloadReceiptPdf(@PathVariable("id") UUID receiptId) {
        ByteArrayInputStream bis = exportReceiptService.exportReceiptToPdf(receiptId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=receipt-" + receiptId + ".pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/history")
    public String receiptHistory(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        List<Receipt> receipts = receiptService.getReceiptsByUserEmail(email);
        model.addAttribute("receipts", receipts);
        return "receipt-history";
    }
} 