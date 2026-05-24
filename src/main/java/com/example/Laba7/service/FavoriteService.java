package com.example.Laba7.service;

import com.example.Laba7.model.Favorite;
import com.example.Laba7.model.Property;
import com.example.Laba7.model.User;
import com.example.Laba7.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Favorite addToFavorites(Long userId, Long propertyId) {
        if (favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId)) {
            throw new RuntimeException("Уже в избранном");
        }
        User user = userService.findById(userId);
        Property property = propertyService.getPropertyById(propertyId);
        return favoriteRepository.save(new Favorite(user, property));
    }

    public void removeFromFavorites(Long userId, Long propertyId) {
        favoriteRepository.deleteByUserIdAndPropertyId(userId, propertyId);
    }

    public List<Property> getUserFavorites(Long userId) {
        User user = userService.findById(userId);
        return favoriteRepository.findByUser(user).stream()
                .map(Favorite::getProperty)
                .collect(Collectors.toList());
    }

    public boolean isFavorite(Long userId, Long propertyId) {
        return favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId);
    }
}