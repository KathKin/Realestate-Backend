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
import com.example.Laba7.dto.PropertyWithApplicationsDto;
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
        Optional<Property> propOpt = propRepo.findById(req.propertyId);
        if (propOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Property prop = propOpt.get();
        Long agentId = (prop.getAgent() != null) ? prop.getAgent().getId() : null;

        if (agentId == null) {
            return ResponseEntity.status(400).build();
        }

        User client = userRepo.findById(req.clientId).orElse(null);

        Application app = new Application();
        app.setPropertyId(req.propertyId);
        app.setAgentId(agentId);
        app.setClientId(req.clientId);

        if (client != null) {
            app.setClientName(client.getFullName());
            app.setClientPhone(client.getPhone() != null ? client.getPhone() : "Не указан");
        } else {
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

    @PutMapping("/{id}/note/client")
    public ResponseEntity<Application> updateClientNote(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        return appRepo.findById(id)
                .map(app -> {
                    app.setClientNote(body.get("note"));
                    return ResponseEntity.ok(appRepo.save(app));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/note/agent")
    public ResponseEntity<Application> updateAgentNote(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        return appRepo.findById(id)
                .map(app -> {
                    app.setAgentNote(body.get("note"));
                    return ResponseEntity.ok(appRepo.save(app));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/agent/{agentId}/properties-with-apps")
    public ResponseEntity<List<PropertyWithApplicationsDto>> getPropertiesWithApps(@PathVariable Long agentId) {
        List<Application> apps = appRepo.findByAgentId(agentId);
        java.util.Map<Long, java.util.List<Application>> grouped = apps.stream()
                .collect(java.util.stream.Collectors.groupingBy(Application::getPropertyId));

        List<PropertyWithApplicationsDto> result = new java.util.ArrayList<>();
        for (java.util.Map.Entry<Long, java.util.List<Application>> entry : grouped.entrySet()) {
            Property prop = propRepo.findById(entry.getKey()).orElse(null);
            if (prop != null) {
                PropertyWithApplicationsDto dto = new PropertyWithApplicationsDto();
                dto.setPropertyId(prop.getId());
                dto.setPropertyTitle(prop.getTitle());
                dto.setPropertyImageUrl(prop.getImageUrl());
                dto.setApplicationsCount(entry.getValue().size());
                result.add(dto);
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Application>> getByProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(appRepo.findByPropertyId(propertyId));
    }

}

class ApplicationRequest {
    public Long propertyId;
    public Long clientId;
    public String clientName;
    public String clientPhone;
    public String message;
}