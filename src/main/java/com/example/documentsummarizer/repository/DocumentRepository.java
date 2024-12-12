package com.example.documentsummarizer.repository;

import com.example.documentsummarizer.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
