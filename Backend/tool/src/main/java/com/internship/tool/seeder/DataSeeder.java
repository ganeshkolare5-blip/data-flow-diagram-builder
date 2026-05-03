package com.internship.tool.seeder;

import com.internship.tool.entity.Diagram;
import com.internship.tool.repository.DiagramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final DiagramRepository diagramRepository;

    @Override
    public void run(String... args) {
        if (diagramRepository.count() == 0) {
            log.info("Seeding 30 diagram records...");
            List<Diagram> diagrams = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                diagrams.add(Diagram.builder()
                        .name("Architecture Diagram " + i)
                        .description("Automated seed data for diagram " + i + " to test scalability and pagination.")
                        .userEmail("tester@example.com")
                        .deadline(LocalDateTime.now().plusDays(i % 7 + 1))
                        .role("USER")
                        .build());
            }
            diagramRepository.saveAll(diagrams);
            log.info("Successfully seeded 30 diagrams.");
        } else {
            log.info("Database already contains data, skipping seeder.");
        }
    }
}
