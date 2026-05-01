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

    // GET ALL
    @GetMapping
    public List<Diagram> getAll() {
        return service.getAllDiagrams();
    }
}