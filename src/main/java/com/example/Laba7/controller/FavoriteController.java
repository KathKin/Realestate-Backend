package com.example.Laba7.controller;

import com.example.Laba7.model.Favorite;
import com.example.Laba7.model.Property;
import com.example.Laba7.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<Favorite> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }

    @PostMapping
    public void addToFavorites(@RequestParam Long userId, @RequestParam Long propertyId) {
        favoriteService.addToFavorites(userId, propertyId);
    }

    @DeleteMapping
    public void removeFromFavorites(@RequestParam Long userId, @RequestParam Long propertyId) {
        favoriteService.removeFromFavorites(userId, propertyId);
    }

    @GetMapping("/{userId}")
    public List<Property> getUserFavorites(@PathVariable Long userId) {
        return favoriteService.getUserFavorites(userId);
    }

    @GetMapping("/check")
    public boolean isFavorite(@RequestParam Long userId, @RequestParam Long propertyId) {
        return favoriteService.isFavorite(userId, propertyId);
    }
}
