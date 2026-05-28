package com.example.Laba7.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String city;

    private String address;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer rooms;

    @Column(nullable = false)
    private Double area;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private User agent;

    @Transient
    private Long agentId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Property() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public BigDecimal getPrice() { return price; }
    public Integer getRooms() { return rooms; }
    public Double getArea() { return area; }
    public String getImageUrl() { return imageUrl; }
    public PropertyType getType() { return type; }
    public User getAgent() { return agent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getAgentId() { return agentId; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCity(String city) { this.city = city; }
    public void setAddress(String address) { this.address = address; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setRooms(Integer rooms) { this.rooms = rooms; }
    public void setArea(Double area) { this.area = area; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setType(PropertyType type) { this.type = type; }
    public void setAgent(User agent) { this.agent = agent; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }
}