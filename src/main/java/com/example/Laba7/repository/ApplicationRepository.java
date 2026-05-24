package com.example.Laba7.repository;

import com.example.Laba7.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Получить все заявки для конкретного риэлтора
    List<Application> findByAgentId(Long agentId);

    // Получить заявки по статусу
    List<Application> findByAgentIdAndStatus(Long agentId, String status);

    // Получить заявки клиента
    List<Application> findByClientId(Long clientId);
}
