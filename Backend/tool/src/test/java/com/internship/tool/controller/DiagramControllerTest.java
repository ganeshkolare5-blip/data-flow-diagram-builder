package com.internship.tool.controller;

import com.internship.tool.dto.DiagramDTO;
import com.internship.tool.entity.Diagram;
import com.internship.tool.service.DiagramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiagramControllerTest {

    @Mock
    private DiagramService diagramService;

    @InjectMocks
    private DiagramController diagramController;

    private Diagram diagram;
    private DiagramDTO diagramDTO;

    @BeforeEach
    void setUp() {
        diagram = Diagram.builder()
                .id(1L)
                .name("Architecture")
                .description("Cloud setup")
                .build();

        diagramDTO = new DiagramDTO();
        diagramDTO.setName("Architecture");
        diagramDTO.setDescription("Cloud setup");
    }

    @Test
    void testGetAllDiagrams() {
        Page<Diagram> page = new PageImpl<>(Collections.singletonList(diagram));
        when(diagramService.getAllDiagrams(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<Diagram>> response = diagramController.getAll(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void testGetDiagramById() {
        when(diagramService.getDiagramById(1L)).thenReturn(diagram);

        ResponseEntity<Diagram> response = diagramController.getDiagram(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Architecture", response.getBody().getName());
    }

    @Test
    void testCreateDiagram() {
        when(diagramService.createDiagram(any(DiagramDTO.class))).thenReturn(diagram);

        ResponseEntity<Diagram> response = diagramController.createDiagram(diagramDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Architecture", response.getBody().getName());
    }

    @Test
    void testDeleteDiagram() {
        doNothing().when(diagramService).deleteDiagram(1L);

        ResponseEntity<Void> response = diagramController.deleteDiagram(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(diagramService, times(1)).deleteDiagram(1L);
    }
}
