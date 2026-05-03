package com.internship.tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;
import com.internship.tool.dto.DiagramDTO;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.exception.InvalidInputException;

@Service
public class DiagramServiceImpl implements DiagramService {

    @Autowired
    private DiagramRepository repository;

    @Override
    public Diagram saveDiagram(Diagram diagram) {
        return repository.save(diagram);
    }

    @Override
    public Page<Diagram> getAllDiagrams(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "diagrams", key = "#id")
    public Diagram getDiagramById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagram not found with ID: " + id));
    }

    @Override
    @CacheEvict(value = "diagrams", key = "#id")
    public void deleteDiagram(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Diagram not found with ID: " + id);
        }
        repository.deleteById(id);
    }
    
    @Override
    @CacheEvict(value = "diagrams", key = "#diagram.id")
    public Diagram updateDiagram(Diagram diagram) {
        if (!repository.existsById(diagram.getId())) {
            throw new ResourceNotFoundException("Diagram not found with ID: " + diagram.getId());
        }
        return repository.save(diagram);
    }
    
    @Override
    public Diagram createDiagram(DiagramDTO dto) {
        // Business Logic Validation Example
        if (!repository.findByNameContainingIgnoreCase(dto.getName()).isEmpty()) {
            throw new InvalidInputException("A diagram with a similar name already exists");
        }
        
        Diagram diagram = Diagram.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .userEmail(dto.getUserEmail())
                .deadline(dto.getDeadline())
                .build();
        return repository.save(diagram);
    }
}