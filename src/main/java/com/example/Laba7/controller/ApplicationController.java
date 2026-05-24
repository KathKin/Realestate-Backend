package com.example.Laba7.controller;

import com.example.Laba7.model.Application;
import com.example.Laba7.model.Property;
import com.example.Laba7.model.User;
import com.example.Laba7.repository.ApplicationRepository;
import com.example.Laba7.repository.PropertyRepository;
import com.example.Laba7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired private ApplicationRepository appRepo;
    @Autowired private PropertyRepository propRepo;
    @Autowired private UserRepository userRepo;

    // Клиент отправляет отклик
    @PostMapping
    public ResponseEntity<Application> create(@RequestBody ApplicationRequest req) {
        // Находим объявление
        Optional<Property> propOpt = propRepo.findById(req.propertyId);
        if (propOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Property prop = propOpt.get();

        // 🔥 ВАЖНО: Получаем агента из объявления
        Long agentId = null;
        if (prop.getAgent() != null) {
            agentId = prop.getAgent().getId();
        }

        if (agentId == null) {
            return ResponseEntity.status(400).build();
        }

        Application app = new Application();
        app.setPropertyId(req.propertyId);
        app.setAgentId(agentId);  // 🔥 Сохраняем ID агента
        app.setClientId(req.clientId);
        app.setClientName(req.clientName);
        app.setClientPhone(req.clientPhone);
        app.setMessage(req.message);
        app.setStatus("NEW");

        Application saved = appRepo.save(app);
        return ResponseEntity.ok(saved);
    }

    // Риэлтор получает свои заявки
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Application>> getByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(appRepo.findByAgentId(agentId));
    }
}

class ApplicationRequest {
    public Long propertyId;
    public Long clientId;
    public String clientName;
    public String clientPhone;
    public String message;
}