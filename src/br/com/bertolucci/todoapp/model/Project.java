package br.com.bertolucci.todoapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private int id;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = LocalDate.parse(createdAt, formatter);
    }

    public String getUpdatedAt() {
        return updatedAt.toString();
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = LocalDate.parse(updatedAt, formatter);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
