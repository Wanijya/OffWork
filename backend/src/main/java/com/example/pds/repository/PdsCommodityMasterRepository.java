package com.example.pds.repository;

import com.example.pds.model.PdsCommodityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsCommodityMasterRepository extends JpaRepository<PdsCommodityMaster, Integer> {
}
