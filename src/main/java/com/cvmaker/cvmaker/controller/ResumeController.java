package com.cvmaker.cvmaker.controller;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("/api/resumes")
public class ResumeController {

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateResume(@RequestParam Map<String, String> formData) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Paragraph title = new Paragraph("Resume", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add personal details
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            document.add(new Paragraph("Personal Details", sectionFont));
            document.add(new Paragraph("Full Name: " + formData.get("fullName"), textFont));
            document.add(new Paragraph("Professional Title: " + formData.get("title"), textFont));
            document.add(new Paragraph("Email: " + formData.get("email"), textFont));
            document.add(new Paragraph("Phone: " + formData.get("phone"), textFont));
            document.add(new Paragraph("Address: " + formData.get("address"), textFont));
            document.add(Chunk.NEWLINE);

            // Add education details
            document.add(new Paragraph("Education", sectionFont));
            int eduIndex = 0;
            while (formData.containsKey("institution[" + eduIndex + "]")) {
                document.add(new Paragraph("Institution: " + formData.get("institution[" + eduIndex + "]"), textFont));
                document.add(new Paragraph("Degree: " + formData.get("degree[" + eduIndex + "]"), textFont));
                document.add(new Paragraph("Start Date: " + formData.get("eduStartDate[" + eduIndex + "]"), textFont));
                document.add(new Paragraph("End Date: " + formData.get("eduEndDate[" + eduIndex + "]"), textFont));
                document.add(Chunk.NEWLINE);
                eduIndex++;
            }

            // Add work experience details
            document.add(new Paragraph("Work Experience", sectionFont));
            int workIndex = 0;
            while (formData.containsKey("company[" + workIndex + "]")) {
                document.add(new Paragraph("Company: " + formData.get("company[" + workIndex + "]"), textFont));
                document.add(new Paragraph("Position: " + formData.get("position[" + workIndex + "]"), textFont));
                document.add(new Paragraph("Start Date: " + formData.get("workStartDate[" + workIndex + "]"), textFont));
                document.add(new Paragraph("End Date: " + formData.get("workEndDate[" + workIndex + "]"), textFont));
                document.add(new Paragraph("Description: " + formData.get("description[" + workIndex + "]"), textFont));
                document.add(Chunk.NEWLINE);
                workIndex++;
            }

            document.close();

            byte[] pdfBytes = out.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "resume.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}