package com.internship.tool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.tool.entity.Diagram;
import com.internship.tool.service.DiagramService;

@RestController
@RequestMapping("/api/diagram")
public class DiagramController {

    @Autowired
    private DiagramService service;

    // ✅ ROOT home CHECK API 
    @GetMapping("/")
    public String home() {
        return "Backend Connected Successfully ";
    }

    @PostMapping("/create")
    public Diagram create(@RequestBody Diagram d) {
        return service.createDiagram(d);   // ✅ correct method name
    }

    @GetMapping("/all")
    public List<Diagram> getAll() {
        return service.getAllDiagrams();   // ✅ correct method name
    }
}