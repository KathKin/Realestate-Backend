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
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired private ApplicationRepository appRepo;
    @Autowired private PropertyRepository propRepo;
    @Autowired private UserRepository userRepo;

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody ApplicationRequest req) {
        // 1. Ищем объявление
        Optional<Property> propOpt = propRepo.findById(req.propertyId);
        if (propOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Property prop = propOpt.get();
        Long agentId = (prop.getAgent() != null) ? prop.getAgent().getId() : null;

        if (agentId == null) {
            return ResponseEntity.status(400).build();
        }

        // 2. 🔧 Ищем Клиента (User), чтобы узнать его Имя и Телефон
        User client = userRepo.findById(req.clientId).orElse(null);

        Application app = new Application();
        app.setPropertyId(req.propertyId);
        app.setAgentId(agentId);
        app.setClientId(req.clientId);

        // 3. 🔧 Заполняем Имя и Телефон из БД (если клиент найден)
        if (client != null) {
            app.setClientName(client.getFullName()); // Проверьте, как называется поле в User.java (getFullName или getName)
            app.setClientPhone(client.getPhone() != null ? client.getPhone() : "Не указан"); // Проверьте поле в User.java
        } else {
            // Если клиент не найден (странно, но бывает), берем из запроса или ставим заглушку
            app.setClientName(req.clientName != null ? req.clientName : "Неизвестно");
            app.setClientPhone(req.clientPhone != null ? req.clientPhone : "Не указан");
        }

        app.setMessage(req.message);
        app.setStatus("NEW");

        Application saved = appRepo.save(app);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Application>> getByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(appRepo.findByAgentId(agentId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Application>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(appRepo.findByClientId(clientId));
    }

    @PutMapping("/{id}/note")
    public ResponseEntity<Application> updateNote(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        return appRepo.findById(id)
                .map(app -> {
                    app.setNote(body.get("note"));
                    return ResponseEntity.ok(appRepo.save(app));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

class ApplicationRequest {
    public Long propertyId;
    public Long clientId;
    public String clientName;
    public String clientPhone;
    public String message;
}