package com.example.pds.repository;

import com.example.pds.model.PdsFpsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsFpsMasterRepository extends JpaRepository<PdsFpsMaster, String> {
    long countByFpsStatus(String fpsStatus); // for the 'Active Shop'
    long countByDistCode(String distCode); // New method: Count FPS by district code
    long countByFpsStatusAndDistCode(String fpsStatus, String distCode); // New method: Count active FPS by status and district code
}
