package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private int projectId;
    private String name;
    private String description;
    private String notes;
    private int status;
    private LocalDate deadline;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeadline() {
        return deadline.toString();
    }

    public void setDeadline(String deadline) {
        this.deadline = LocalDate.parse(deadline, formatter);
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
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                ", deadline=" + deadline +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
