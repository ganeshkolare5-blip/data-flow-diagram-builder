package com.internship.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.internship.tool.entity.Diagram;

import java.util.List;

@Repository
public interface DiagramRepository extends JpaRepository<Diagram, Long> {
    
    List<Diagram> findByNameContainingIgnoreCase(String name);
    
    List<Diagram> findByRole(String role);
    
    @Query("SELECT d FROM Diagram d WHERE d.description IS NOT NULL AND d.description != ''")
    List<Diagram> findDiagramsWithDescription();
}