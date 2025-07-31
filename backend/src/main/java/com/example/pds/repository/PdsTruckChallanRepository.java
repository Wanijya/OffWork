package com.example.pds.repository;

import com.example.pds.model.PdsTruckChallan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsTruckChallanRepository extends JpaRepository<PdsTruckChallan, String> {


//    long countByEntryData(java.time.LocalDate entryDate);
}
