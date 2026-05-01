package com.internship.tool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;   // ✅ IMPORTANT import

import com.internship.tool.entity.Diagram;
import com.internship.tool.service.DiagramService;
import com.internship.tool.dto.DiagramDTO;

@RestController
@RequestMapping("/diagrams")
public class DiagramController {

    @Autowired
    private DiagramService service;

    // CREATE
    @PostMapping
    public Diagram create(@Valid @RequestBody DiagramDTO dto) {
        Diagram diagram = new Diagram();
        diagram.setName(dto.getName());
        diagram.setDescription(dto.getDescription());

        return service.createDiagram(diagram);  // ✅ FIXED
    }
    
    @PutMapping("/{id}")
    public Diagram update(@PathVariable Long id, @RequestBody Diagram diagram) {
        return service.updateDiagram(id, diagram);
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteDiagram(id);
        return "Deleted Successfully";
    }
    
    @GetMapping("/{id}")
    public Diagram getDiagramById(@PathVariable Long id) {
        return service.getDiagramById(id); 
    }

    // GET ALL
    @GetMapping
    public List<Diagram> getAll() {
        return service.getAllDiagrams();
    }
}