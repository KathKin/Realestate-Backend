package com.example.Laba7.repository;

import com.example.Laba7.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByAgentId(Long agentId);

    List<Application> findByAgentIdAndStatus(Long agentId, String status);

    @Query("SELECT a FROM Application a JOIN FETCH a.property WHERE a.clientId = :clientId")
    List<Application> findByClientId(@Param("clientId") Long clientId);

    List<Application> findByPropertyId(Long propertyId);
}
