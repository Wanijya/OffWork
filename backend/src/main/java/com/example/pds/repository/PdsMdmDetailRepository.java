package com.example.pds.repository;

import com.example.pds.model.PdsMdmDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsMdmDetailRepository extends JpaRepository<PdsMdmDetail, String> {
}
