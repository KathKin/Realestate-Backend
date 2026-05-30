package com.example.Laba7.service;

import com.example.Laba7.model.Application;
import com.example.Laba7.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    public List<Application> findByClientId(Long clientId) {
        return repository.findByClientId(clientId);
    }

    public Optional<Application> findById(Long id) {
        return repository.findById(id);
    }

    public Application save(Application application) {
        return repository.save(application);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Application updateNote(Long id, String note) {
        return repository.findById(id)
                .map(application -> {
                    application.setClientNote(note);
                    return repository.save(application);
                })
                .orElse(null);
    }
}
