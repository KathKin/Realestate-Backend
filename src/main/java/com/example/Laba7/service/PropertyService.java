package com.example.Laba7.service;

import com.example.Laba7.model.Property;
import com.example.Laba7.model.User;
import com.example.Laba7.repository.PropertyRepository;
import com.example.Laba7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
    }

    public Property createProperty(Property property) {
        if (property.getAgentId() != null && property.getAgent() == null) {
            User agent = userRepository.findById(property.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Агент с ID " + property.getAgentId() + " не найден"));
            property.setAgent(agent);
        }

        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property propertyDetails) {
        Property property = getPropertyById(id);
        property.setTitle(propertyDetails.getTitle());
        property.setDescription(propertyDetails.getDescription());
        property.setCity(propertyDetails.getCity());
        property.setAddress(propertyDetails.getAddress());
        property.setPrice(propertyDetails.getPrice());
        property.setRooms(propertyDetails.getRooms());
        property.setArea(propertyDetails.getArea());
        property.setType(propertyDetails.getType());
        return propertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    public List<Property> searchProperties(String city, BigDecimal minPrice,
                                           BigDecimal maxPrice, Integer rooms) {
        return propertyRepository.searchProperties(city, minPrice, maxPrice, rooms);
    }

    public List<Property> getPropertiesByAgent(Long agentId) {
        return propertyRepository.findByAgentId(agentId);
    }
}