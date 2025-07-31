package com.example.pds.repository;

import com.example.pds.model.PdsTruckChallanDetail;
import com.example.pds.model.PdsTruckChallanDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PdsTruckChallanDetailsRepository extends JpaRepository<PdsTruckChallanDetail, PdsTruckChallanDetailId> {

    @Query(value = "SELECT SUM(t.dispatched) FROM epos.truck_challan_details t", nativeQuery = true)
    Double sumDispatchedQuantity();

    @Query(value = "SELECT SUM(t.received) FROM epos.truck_challan_details t", nativeQuery = true)
    Double sumReceivedQuantity();

}
