package com.internship.tool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.internship.tool.entity.Diagram;
import com.internship.tool.dto.DiagramDTO;

public interface DiagramService {
    Diagram saveDiagram(Diagram diagram);
    Page<Diagram> getAllDiagrams(Pageable pageable);
    Diagram getDiagramById(Long id);
    void deleteDiagram(Long id);
    Diagram updateDiagram(Diagram diagram);
    Diagram createDiagram(DiagramDTO dto);
}