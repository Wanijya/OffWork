package com.example.pds.repository;

import com.example.pds.model.PdsMstWelfareInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsMstWelfareInstituteRepository extends JpaRepository<PdsMstWelfareInstitute, String> {
}
