package com.internship.tool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.tool.entity.DfdRecord;
import com.internship.tool.repository.DfdRecordRepository;

@Service
public class DfdRecordService {

    @Autowired
    private DfdRecordRepository repository;

    @Autowired
    private AiServiceClient aiService;

    public DfdRecord create(DfdRecord record) {
        // If there's an AI call needed, we could do it here
        // e.g. String aiDesc = aiService.getAiResponse(record.getDescription());
        // if (aiDesc != null) record.setDescription(aiDesc);
        
        return repository.save(record);
    }

    public List<DfdRecord> getAll() {
        return repository.findByDeletedFalse();
    }

    public DfdRecord update(Long id, DfdRecord updatedRecord) {
        DfdRecord existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Record not found"));

        existing.setTitle(updatedRecord.getTitle());
        existing.setDescription(updatedRecord.getDescription());
        existing.setStatus(updatedRecord.getStatus());
        if (updatedRecord.getPriority() != null) {
            existing.setPriority(updatedRecord.getPriority());
        }

        return repository.save(existing);
    }

    public void softDelete(Long id) {
        DfdRecord record = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setDeleted(true);
        repository.save(record);
    }
}
