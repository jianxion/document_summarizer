package com.example.documentsummarizer.controller;

import com.example.documentsummarizer.model.Document;
import com.example.documentsummarizer.repository.DocumentRepository;
import com.example.documentsummarizer.service.SummarizationService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private SummarizationService summarizationService;

    @Autowired
    private DocumentRepository documentRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty!");
        }

        try {
            String extractedText = "";

            if (file.getOriginalFilename().endsWith(".pdf")) {
                extractedText = extractTextFromPdf(file);
            } else if (file.getOriginalFilename().endsWith(".docx")) {
                extractedText = extractTextFromWord(file);
            } else {
                return ResponseEntity.badRequest().body("Unsupported file type!");
            }

            // Call the summarization service
            String summary = summarizationService.summarizeText(extractedText);

            // Save to database
            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setExtractedText(extractedText);
            document.setSummary(summary);

            documentRepository.save(document);

            return ResponseEntity.ok("Summary: " + summary);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to process file!");
        }
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }

    private String extractTextFromWord(MultipartFile file) throws IOException {
        XWPFDocument document = new XWPFDocument(file.getInputStream());
        StringBuilder text = new StringBuilder();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            text.append(paragraph.getText()).append("\n");
        }
        return text.toString();
    }
}
