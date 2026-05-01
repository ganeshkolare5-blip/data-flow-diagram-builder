package com.internship.tool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;

@Service
public class DiagramServiceImpl implements DiagramService {

    @Autowired
    private DiagramRepository repository;

    @Override
    public Diagram createDiagram(Diagram diagram) {
        return repository.save(diagram);
    }

    @Override
    public List<Diagram> getAllDiagrams() {
        return repository.findAll();
    }

    @Override
    public Diagram getDiagramById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagram not found"));
    }

    @Override
    public Diagram updateDiagram(Long id, Diagram updatedDiagram) {
        Diagram existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagram not found"));

        existing.setName(updatedDiagram.getName());
        existing.setDescription(updatedDiagram.getDescription()); // ✅ FIXED

        return repository.save(existing);
    }

    @Override
    public void deleteDiagram(Long id) {
        repository.deleteById(id);
    }
}