package com.example.Laba7.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    // Конструкторы
    public Favorite() {}

    public Favorite(User user, Property property) {
        this.user = user;
        this.property = property;
    }

    // Геттеры
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Property getProperty() { return property; }

    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setProperty(Property property) { this.property = property; }
}