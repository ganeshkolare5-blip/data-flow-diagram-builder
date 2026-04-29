package com.internship.tool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;

@Service
public class DiagramService {

    @Autowired
    private DiagramRepository repo;

    @Autowired
    private AiServiceClient aiClient;

    public Diagram create(Diagram d) {

        String input = d.getName() + " " + d.getDescription();

        String result = aiClient.getAiResponse(input);

        if (result != null) {
            d.setAiResult(result);
        } else {
            d.setAiResult("AI unavailable");
        }

        return repo.save(d);
    }

    public List<Diagram> getAll() {
        return repo.findAll();
    }
}