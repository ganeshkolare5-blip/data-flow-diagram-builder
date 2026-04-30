package com.internship.tool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internship.tool.entity.DfdRecord;
import com.internship.tool.repository.DfdRecordRepository;
import com.internship.tool.service.DfdRecordService;

@RestController
@RequestMapping("/api/diagram")
public class DfdRecordController {

    @Autowired
    private DfdRecordService service;
    
    @Autowired
    private DfdRecordRepository repository;

    @GetMapping("/")
    public String home() {
        return "Backend Connected Successfully";
    }

    @PostMapping("/create")
    public DfdRecord create(@RequestBody DfdRecord record) {
        return service.create(record);
    }

    @GetMapping("/all")
    public List<DfdRecord> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody DfdRecord updatedRecord
    ) {
        return ResponseEntity.ok(service.update(id, updatedRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.softDelete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/search")
    public List<DfdRecord> search(@RequestParam String q) {
        return repository.findByTitleContainingIgnoreCaseAndDeletedFalse(q);
    }
}
