package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportCartService {
    private final CartRepository cartRepository;
    public ByteArrayInputStream exportPatientRecordToPdf(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Font vietnameseFont;
        try {
            String fontPath = "fonts/arial.ttf";
            BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            vietnameseFont = new Font(baseFont, 12);

        } catch (Exception e) {
            System.err.println("Custom font not found. Using a fallback font.");
            vietnameseFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        }
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            Font titleFont = new Font(vietnameseFont.getFamily(), 18, Font.BOLD, BaseColor.BLACK);
            //title
            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", vietnameseFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);
            //products
            document.add(createSectionTitle("THÔNG TIN KHÁCH HÀNG", vietnameseFont));
            document.add(new LineSeparator());
            document.add(Chunk.NEWLINE);
            //
            PdfPTable productTable = new PdfPTable(5);
            productTable.setWidthPercentage(100);
            productTable.setWidths(new float[]{0.25f, 0.15f, 0.15f, 0.15f, 0.15f});
            productTable.setSpacingBefore(5f);

// Header
            addHeaderCell(productTable, "Tên Sản phẩm", vietnameseFont);
            addHeaderCell(productTable, "Số lượng", vietnameseFont);
            addHeaderCell(productTable, "Đơn giá", vietnameseFont);
            addHeaderCell(productTable, "Tổng giá", vietnameseFont);

// Rows
            List<CartItem> items1 = cart.getItems();
            if(items1 == null){
                addCell(productTable, "No product found", vietnameseFont);
            }
            else{
                for (CartItem m : items1) {
                    addCell(productTable, m.getProduct().getName(), vietnameseFont);
                    addCell(productTable, String.valueOf(m.getQuantity()), vietnameseFont);
                    addCell(productTable, String.valueOf(m.getProduct().getPrice()), vietnameseFont);
                    addCell(productTable, String.valueOf(m.getTotalPrice()), vietnameseFont);
                }
            }
            document.add(productTable);


        }catch (DocumentException ex) {
            ex.printStackTrace();
        }finally {
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
