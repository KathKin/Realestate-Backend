package com.example.Laba7.controller;

import com.example.Laba7.model.Property;
import com.example.Laba7.repository.PropertyRepository;
import com.example.Laba7.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.createProperty(property);
    }

    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Long id, @RequestBody Property property) {
        return propertyService.updateProperty(id, property);
    }

    @GetMapping("/search")
    public List<Property> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer rooms) {
        return propertyService.searchProperties(city, minPrice, maxPrice, rooms);
    }

    @GetMapping("/agent/{agentId}")
    public List<Property> getPropertiesByAgent(@PathVariable Long agentId) {
        return propertyService.getPropertiesByAgent(agentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProperty(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Проверяем, существует ли объявление
            Optional<Property> propertyOpt = propertyRepository.findById(id);

            if (propertyOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Объявление с ID " + id + " не найдено");
                return ResponseEntity.status(404).body(response);
            }

            propertyRepository.deleteById(id);

            response.put("success", true);
            response.put("message", "Объявление успешно удалено");
            response.put("deletedId", id);

            System.out.println(" Объявление #" + id + " удалено");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка при удалении: " + e.getMessage());
            System.err.println(" Ошибка удаления объявления #" + id + ": " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }
}