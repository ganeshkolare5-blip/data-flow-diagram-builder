package com.internship.tool.dto;

import jakarta.validation.constraints.NotBlank;

public class DiagramDTO {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    private String description;

    private String userEmail;
    private java.time.LocalDateTime deadline;

    // Default constructor
    public DiagramDTO() {}
    

    // Parameterized constructor (optional)
    public DiagramDTO(Long id, String name, String description, String userEmail, java.time.LocalDateTime deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userEmail = userEmail;
        this.deadline = deadline;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public java.time.LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(java.time.LocalDateTime deadline) {
        this.deadline = deadline;
    }
}