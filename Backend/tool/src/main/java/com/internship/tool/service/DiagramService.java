package com.internship.tool.service;

import java.util.List;
import com.internship.tool.entity.Diagram;

public interface DiagramService {

    Diagram createDiagram(Diagram diagram);

    List<Diagram> getAllDiagrams();

    Diagram getDiagramById(Long id);

    Diagram updateDiagram(Long id, Diagram diagram);

    void deleteDiagram(Long id);
}