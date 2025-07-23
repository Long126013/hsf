package com.example.demo.service;

import com.example.demo.model.Receipt;
import com.example.demo.model.ReceiptItem;
import com.example.demo.repository.ReceiptRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportReceiptService {
    private final ReceiptRepository receiptRepository;

    public ByteArrayInputStream exportReceiptToPdf(UUID receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId).orElseThrow();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Font vietnameseFont;
        Font titleFont;
        Font headerFont;
        Font totalFont;
        try {
            String arialPath = "font/arial.ttf";
            String timesPath = "font/Times New Roman.ttf";
            BaseFont arialBase = BaseFont.createFont(arialPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont timesBase = BaseFont.createFont(timesPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            vietnameseFont = new Font(arialBase, 12);
            titleFont = new Font(timesBase, 18, Font.BOLD);
            headerFont = new Font(timesBase, 13, Font.BOLD);
            totalFont = new Font(timesBase, 14, Font.BOLD);
        } catch (Exception e) {
            System.err.println("Custom font not found. Using a fallback font.");
            vietnameseFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            titleFont = new Font(FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));
            headerFont = new Font(FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD));
            totalFont = new Font(FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
        }
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            // Title
            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);
            // Customer info
            document.add(createSectionTitle("THÔNG TIN KHÁCH HÀNG", vietnameseFont));
            document.add(new LineSeparator());
            document.add(new Paragraph("Email: " + receipt.getUser().getEmail(), vietnameseFont));
            document.add(new Paragraph("Ngày tạo: " + receipt.getCreatedDate(), vietnameseFont));
            document.add(Chunk.NEWLINE);
            // Product table
            PdfPTable productTable = new PdfPTable(4);
            productTable.setWidthPercentage(100);
            productTable.setWidths(new float[]{0.4f, 0.15f, 0.2f, 0.25f});
            productTable.setSpacingBefore(5f);
            // Header
            addHeaderCell(productTable, "Tên Sản phẩm", headerFont);
            addHeaderCell(productTable, "Số lượng", headerFont);
            addHeaderCell(productTable, "Đơn giá", headerFont);
            addHeaderCell(productTable, "Tổng giá", headerFont);
            // Rows
            List<ReceiptItem> items = receipt.getItems();
            if (items == null || items.isEmpty()) {
                addCell(productTable, "No product found", vietnameseFont);
            } else {
                for (ReceiptItem m : items) {
                    addCell(productTable, m.getProductName(), vietnameseFont);
                    addCell(productTable, String.valueOf(m.getQuantity()), vietnameseFont);
                    addCell(productTable, String.valueOf(m.getPrice()), vietnameseFont);
                    addCell(productTable, String.valueOf(m.getTotalPrice()), vietnameseFont);
                }
            }
            document.add(productTable);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Tổng thanh toán: " + receipt.getTotal() + " VND", totalFont));
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private Paragraph createSectionTitle(String title, Font font) {
        Font sectionFont = new Font(font.getBaseFont(), 14, Font.BOLD);
        Paragraph p = new Paragraph(title, sectionFont);
        p.setSpacingBefore(20f);
        p.setSpacingAfter(10f);
        return p;
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        cell.setBackgroundColor(new BaseColor(220, 220, 220)); // light gray
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5f);
        table.addCell(cell);
    }
} 