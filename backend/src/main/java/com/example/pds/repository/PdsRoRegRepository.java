package com.example.pds.repository;

import com.example.pds.model.PdsRoReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsRoRegRepository extends JpaRepository<PdsRoReg, String> {
}
