package com.internship.tool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.tool.entity.DfdRecord;
import com.internship.tool.repository.DfdRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Day 8 — MockMvc Integration Tests for DfdRecordController
 *
 * Compatible with Spring Boot 4.x which removed @AutoConfigureMockMvc
 * in favour of manual MockMvcBuilders.webAppContextSetup().
 *
 * Covers:
 *   GET  /api/diagram/all
 *   GET  /api/diagram/{id}         (found & not-found)
 *   POST /api/diagram/create       (201 CREATED)
 *   PUT  /api/diagram/{id}         (found & not-found)
 *   DELETE /api/diagram/{id}       (soft-delete & not-found)
 *   GET  /api/diagram/search?q=    (match, no-match, case-insensitive)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DfdControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DfdRecordRepository repository;

    // Instantiate directly — ObjectMapper is not a bean in this restricted test context
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private MockMvc mockMvc;
    private DfdRecord savedRecord;

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private DfdRecord buildRecord(String title, String description, String status) {
        DfdRecord r = new DfdRecord();
        r.setTitle(title);
        r.setDescription(description);
        r.setStatus(status);
        r.setPriority("HIGH");
        return r;
    }

    @BeforeEach
    void setUp() {
        // Build MockMvc manually — required in Spring Boot 4 (no @AutoConfigureMockMvc)
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        repository.deleteAll();
        savedRecord = repository.save(buildRecord("Test Diagram", "A sample DFD", "ACTIVE"));
    }

    // ─── GET /all ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /all → 200 OK and returns list")
    void shouldReturnAllRecords() throws Exception {
        mockMvc.perform(get("/api/diagram/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title", is("Test Diagram")));
    }

    @Test
    @DisplayName("GET /all → does not return soft-deleted records")
    void shouldNotReturnDeletedRecords() throws Exception {
        savedRecord.setDeleted(true);
        repository.save(savedRecord);

        mockMvc.perform(get("/api/diagram/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ─── GET /{id} ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /{id} → 200 OK when record exists")
    void shouldReturnRecordById() throws Exception {
        mockMvc.perform(get("/api/diagram/" + savedRecord.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedRecord.getId().intValue())))
                .andExpect(jsonPath("$.title", is("Test Diagram")));
    }

    @Test
    @DisplayName("GET /{id} → 404 when record does not exist")
    void shouldReturn404WhenRecordNotFound() throws Exception {
        mockMvc.perform(get("/api/diagram/99999"))
                .andExpect(status().isNotFound());
    }

    // ─── POST /create ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /create → 201 CREATED with saved record in body")
    void shouldCreateRecord() throws Exception {
        String json = objectMapper.writeValueAsString(
                buildRecord("New Diagram", "Created via test", "ACTIVE"));

        mockMvc.perform(post("/api/diagram/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("New Diagram")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }

    @Test
    @DisplayName("POST /create → record is persisted in DB")
    void shouldPersistCreatedRecord() throws Exception {
        long countBefore = repository.count();

        String json = objectMapper.writeValueAsString(
                buildRecord("Persisted Diagram", "Should be in DB", "INACTIVE"));

        mockMvc.perform(post("/api/diagram/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        org.junit.jupiter.api.Assertions.assertEquals(countBefore + 1, repository.count());
    }

    // ─── PUT /{id} ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("PUT /{id} → 200 OK with updated record")
    void shouldUpdateRecord() throws Exception {
        DfdRecord updated = buildRecord("Updated Title", "Updated description", "COMPLETED");
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/api/diagram/" + savedRecord.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.status", is("COMPLETED")));
    }

    @Test
    @DisplayName("PUT /{id} → 404 when record does not exist")
    void shouldReturn404WhenUpdatingNonExistent() throws Exception {
        String json = objectMapper.writeValueAsString(
                buildRecord("Ghost", "does not exist", "ACTIVE"));

        mockMvc.perform(put("/api/diagram/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE /{id} ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /{id} → 200 OK and record is soft-deleted")
    void shouldSoftDeleteRecord() throws Exception {
        mockMvc.perform(delete("/api/diagram/" + savedRecord.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Deleted")));

        DfdRecord inDb = repository.findById(savedRecord.getId()).orElseThrow();
        org.junit.jupiter.api.Assertions.assertTrue(inDb.getDeleted());
    }

    @Test
    @DisplayName("DELETE /{id} → 404 when record does not exist")
    void shouldReturn404WhenDeletingNonExistent() throws Exception {
        mockMvc.perform(delete("/api/diagram/99999"))
                .andExpect(status().isNotFound());
    }

    // ─── GET /search ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /search?q= → 200 OK with matching results")
    void shouldSearchRecords() throws Exception {
        mockMvc.perform(get("/api/diagram/search").param("q", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title", containsStringIgnoringCase("Test")));
    }

    @Test
    @DisplayName("GET /search?q= → 200 OK with empty list for no match")
    void shouldReturnEmptyListForNoMatch() throws Exception {
        mockMvc.perform(get("/api/diagram/search").param("q", "XYZNONEXISTENT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /search?q= → case-insensitive matching")
    void shouldSearchCaseInsensitively() throws Exception {
        mockMvc.perform(get("/api/diagram/search").param("q", "test")) // lowercase
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
}
