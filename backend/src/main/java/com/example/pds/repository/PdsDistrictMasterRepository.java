package com.example.pds.repository;

import com.example.pds.model.PdsDistrictMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsDistrictMasterRepository extends JpaRepository<PdsDistrictMaster, String> {
    // Spring Data JPA automatically provides methods
}
