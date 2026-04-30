package com.internship.tool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;
import com.internship.tool.exception.InvalidInputException;
import com.internship.tool.exception.ResourceNotFoundException;

@Service
public class DiagramService {

    @Autowired
    private DiagramRepository diagramRepository;

    // CREATE
    public Diagram createDiagram(Diagram diagram) {

        // 🔴 Manual Validation
        if (diagram.getName() == null || diagram.getName().trim().isEmpty()) {
            throw new InvalidInputException("Diagram name cannot be empty");
        }

        if (diagram.getName().length() < 3) {
            throw new InvalidInputException("Diagram name must be at least 3 characters");
        }

        if (diagram.getDescription() != null && diagram.getDescription().length() > 500) {
            throw new InvalidInputException("Description cannot exceed 500 characters");
        }

        return diagramRepository.save(diagram);
    }

    // GET ALL
    public List<Diagram> getAllDiagrams() {
        return diagramRepository.findAll();
    }

    // GET BY ID
    public Diagram getDiagramById(Long id) {
        return diagramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagram not found with id: " + id));
    }

    // UPDATE
    public Diagram updateDiagram(Long id, Diagram updatedDiagram) {

        Diagram existing = getDiagramById(id);

        if (updatedDiagram.getName() == null || updatedDiagram.getName().trim().isEmpty()) {
            throw new InvalidInputException("Diagram name cannot be empty");
        }

        existing.setName(updatedDiagram.getName());
        existing.setDescription(updatedDiagram.getDescription());

        return diagramRepository.save(existing);
    }

    // DELETE
    public void deleteDiagram(Long id) {
        Diagram diagram = getDiagramById(id);
        diagramRepository.delete(diagram);
    }
}