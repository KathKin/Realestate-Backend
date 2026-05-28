package com.example.Laba7.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_id")
    private Long propertyId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private Long agentId;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPhone;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private String status = "NEW";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", insertable = false, updatable = false)
    private Property property;

    public Application() {
    }

    public Application(Long propertyId, Long clientId, Long agentId,
                       String clientName, String clientPhone, String message) {
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.agentId = agentId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.message = message;
        this.status = "NEW";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getNote() {
        return note;
    }

    public Property getProperty() {
        return property;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", propertyId=" + propertyId +
                ", clientId=" + clientId +
                ", agentId=" + agentId +
                ", clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    // Метод для обновления статуса
    public void updateStatus(String newStatus) {
        if ("VIEWED".equals(newStatus) || "CONTACTED".equals(newStatus) || "CLOSED".equals(newStatus)) {
            this.status = newStatus;
        }
    }
}
