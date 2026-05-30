package com.example.Laba7.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(name = "viewing_date", nullable = false)
    private LocalDateTime viewingDate;

    private String status;

    @Column(name = "client_phone")
    private String clientPhone;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Booking() {}

    public Booking(Property property, User client, LocalDateTime viewingDate,
                   String clientPhone, String clientName) {
        this.property = property;
        this.client = client;
        this.viewingDate = viewingDate;
        this.clientPhone = clientPhone;
        this.clientName = clientName;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Property getProperty() { return property; }
    public User getClient() { return client; }
    public LocalDateTime getViewingDate() { return viewingDate; }
    public String getStatus() { return status; }
    public String getClientPhone() { return clientPhone; }
    public String getClientName() { return clientName; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setProperty(Property property) { this.property = property; }
    public void setClient(User client) { this.client = client; }
    public void setViewingDate(LocalDateTime viewingDate) { this.viewingDate = viewingDate; }
    public void setStatus(String status) { this.status = status; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}