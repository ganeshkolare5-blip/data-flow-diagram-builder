package com.internship.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.internship.tool.entity.Diagram;

public interface DiagramRepository extends JpaRepository<Diagram, Long> {
}