package com.example.pds.repository;

import com.example.pds.model.PdsIcdsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsIcdsDetailRepository extends JpaRepository<PdsIcdsDetail, String> {
}