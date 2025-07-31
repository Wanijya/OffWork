package com.example.pds.service;

import com.example.pds.repository.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdsDashboardService {

    @Autowired
    private PdsFpsMasterRepository fpsMasterRepository;

    @Autowired
    private PdsIcdsDetailRepository pdsIcdsDetailRepository;

    @Autowired
    private PdsMdmDetailRepository pdsMdmDetailRepository;

    @Autowired
    private PdsMstWelfareInstituteRepository pdsMstWelfareInstituteRepository;

    @Autowired
    private PdsRoRegRepository pdsRoRegRepository;

    @Autowired
    private PdsTruckChallanDetailsRepository pdsTruckChallanDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String ROLE_ADMIN = "1";
    private static final String ROLE_HO_USER = "4";
    private static final String ROLE_DEPOT_USER = "8";

    public Map<String, Object> getPdsDashboardMetrics(String roleId, String distCode) {
        Map<String, Object> metrics = new HashMap<>();

        long totalShops = 0;
        long activeShops = 0;
        long totalMdmEntries = 0;
        long totalIcdsEntries = 0;
        long totalWelfareInstitutes = 0;
        long totalRoGenerated = 0;
        Double dispatchedQuantity = 0.0;
        Double receivedQuantity = 0.0;

        // Logic to filter based on roleId and distCode
        if (ROLE_ADMIN.equals(roleId) || ROLE_HO_USER.equals(roleId)) {
            // Admin and HO users see all global data
            totalShops = fpsMasterRepository.count();
            activeShops = fpsMasterRepository.countByFpsStatus("A");
            totalMdmEntries = countMdmEntries(null); // Pass null to get global count
            totalIcdsEntries = countIcdsEntries(null); // Pass null to get global count
            totalWelfareInstitutes = countWelfareInstitutes(null); // Pass null to get global count
            totalRoGenerated = countRoGenerated(null); // Pass null to get global count
            dispatchedQuantity = sumDispatchedQuantity(null); // Pass null to get global sum
            receivedQuantity = sumReceivedQuantity(null); // Pass null to get global sum

        } else if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            // Depot users see data only for their assigned district
            totalShops = fpsMasterRepository.countByDistCode(distCode);
            activeShops = fpsMasterRepository.countByFpsStatusAndDistCode("A", distCode);

            // For MDM, ICDS, Welfare, RO, Dispatched, Received,
            // we use helper methods with native queries to filter by distCode
            totalMdmEntries = countMdmEntries(distCode);
            totalIcdsEntries = countIcdsEntries(distCode);
            totalWelfareInstitutes = countWelfareInstitutes(distCode);
            totalRoGenerated = countRoGenerated(distCode);
            dispatchedQuantity = sumDispatchedQuantity(distCode);
            receivedQuantity = sumReceivedQuantity(distCode);

        } else {
            // Default or unhandled role/scenario, return zero or handle as an error
            System.err.println("Unhandled role or missing district code for role: " + roleId + ", distCode: " + distCode);
            // Optionally, throw an exception or return an error state
        }

        metrics.put("totalShops", totalShops);
        metrics.put("activeShops", activeShops);
        metrics.put("totalMdmEntries", totalMdmEntries);
        metrics.put("totalIcdsEntries", totalIcdsEntries);
        metrics.put("totalWelfareInstitutes", totalWelfareInstitutes);
        metrics.put("totalRoGenerated", totalRoGenerated);
        metrics.put("totalDispatchedQuantity", dispatchedQuantity != null ? String.format("%.2f", dispatchedQuantity) : "0.00");
        metrics.put("totalReceivedQuantity", receivedQuantity != null ? String.format("%.2f", receivedQuantity) : "0.00");

        metrics.put("lastRefreshed", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return metrics;
    }

    // Helper methods for counts/sums, now accepting optional distCode
    // If distCode is null, it means global count. If not null, filter by distCode.

    private long countMdmEntries(String distCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(mdm.school_id) FROM epos.mdm_detail mdm ");
        if (distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE mdm.dist_code = :distCode");
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    private long countIcdsEntries(String distCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(icds.icds_id) FROM epos.icds_detail icds ");
        if (distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE icds.dist_code = :distCode");
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    private long countWelfareInstitutes(String distCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(mwi.institute_id) FROM scm.mst_welfare_institute mwi ");
        if (distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE mwi.district_code = :distCode");
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    private long countRoGenerated(String distCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(rr.ro_no) FROM scm.ro_reg rr JOIN common.fps_master fm ON rr.shop_no = fm.fps_id ");
        if (distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode");
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    private Double sumDispatchedQuantity(String distCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(tcd.dispatched) FROM epos.truck_challan_details tcd JOIN epos.truck_challan tc ON tcd.truckchitno = tc.truckchitno JOIN common.fps_master fm ON tc.fps_id = fm.fps_id ");
        if (distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode");
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).doubleValue() : 0.0;
    }

    private Double sumReceivedQuantity(String distCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(tcd.received) FROM epos.truck_challan_details tcd JOIN epos.truck_challan tc ON tcd.truckchitno = tc.truckchitno JOIN common.fps_master fm ON tc.fps_id = fm.fps_id ");
        if (distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode");
        }
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).doubleValue() : 0.0;
    }


    // Existing getShopsByDistrict method modified to accept roleId and distCode
    public List<Map<String, Object>> getShopsByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, COUNT(fm.fps_id) as total_shops ");
        sqlBuilder.append("FROM common.fps_master fm ");
        sqlBuilder.append("JOIN common.district_master dm ON fm.dist_code = dm.dist_code ");

        // Apply filtering for Depot users
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode "); // <-- Crucial WHERE clause
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode); // <-- Setting parameter
        }

        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalShops", ((Number) row[1]).longValue());
            return map;
        }).collect(Collectors.toList());
    }

    // Modify other detail methods similarly
    public List<Map<String, Object>> getMdmEntriesByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, COUNT(mdm.school_id) as total_mdm_entries ");
        sqlBuilder.append("FROM epos.mdm_detail mdm ");
        sqlBuilder.append("JOIN common.district_master dm ON mdm.dist_code = dm.dist_code ");

        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE mdm.dist_code = :distCode ");
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalMdmEntries", ((Number) row[1]).longValue());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getIcdsEntriesByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, COUNT(icds.icds_id) as total_icds_entries ");
        sqlBuilder.append("FROM epos.icds_detail icds ");
        sqlBuilder.append("JOIN common.district_master dm ON icds.dist_code = dm.dist_code ");

        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE icds.dist_code = :distCode ");
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalIcdsEntries", ((Number) row[1]).longValue());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getWelfareInstitutesByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, COUNT(mwi.institute_id) as total_welfare_institutes ");
        sqlBuilder.append("FROM scm.mst_welfare_institute mwi ");
        sqlBuilder.append("JOIN common.district_master dm ON mwi.district_code = dm.dist_code ");

        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE mwi.district_code = :distCode ");
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalWelfareInstitutes", ((Number) row[1]).longValue());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRoGeneratedByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, COUNT(rr.ro_no) as total_ro_generated ");
        sqlBuilder.append("FROM scm.ro_reg rr ");
        sqlBuilder.append("JOIN common.fps_master fm ON rr.shop_no = fm.fps_id ");
        sqlBuilder.append("JOIN common.district_master dm ON fm.dist_code = dm.dist_code ");

        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode ");
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalRoGenerated", ((Number) row[1]).longValue());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getDispatchedQtyByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, SUM(tcd.dispatched) as total_dispatched_qty ");
        sqlBuilder.append("FROM epos.truck_challan_details tcd ");
        sqlBuilder.append("JOIN epos.truck_challan tc ON tcd.truckchitno = tc.truckchitno ");
        sqlBuilder.append("JOIN common.fps_master fm ON tc.fps_id = fm.fps_id ");
        sqlBuilder.append("JOIN common.district_master dm ON fm.dist_code = dm.dist_code ");

        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode ");
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalDispatchedQty", ((Number) row[1]).doubleValue());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getReceivedQtyByDistrict(String roleId, String distCode) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT dm.dist_name_en, SUM(tcd.received) as total_received_qty ");
        sqlBuilder.append("FROM epos.truck_challan_details tcd ");
        sqlBuilder.append("JOIN epos.truck_challan tc ON tcd.truckchitno = tc.truckchitno ");
        sqlBuilder.append("JOIN common.fps_master fm ON tc.fps_id = fm.fps_id ");
        sqlBuilder.append("JOIN common.district_master dm ON fm.dist_code = dm.dist_code ");

        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            sqlBuilder.append("WHERE fm.dist_code = :distCode ");
        }
        sqlBuilder.append("GROUP BY dm.dist_name_en ");
        sqlBuilder.append("ORDER BY dm.dist_name_en");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        if (ROLE_DEPOT_USER.equals(roleId) && distCode != null && !distCode.isEmpty()) {
            query.setParameter("distCode", distCode);
        }
        List<Object[]> results = query.getResultList();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("districtName", row[0]);
            map.put("totalReceivedQty", ((Number) row[1]).doubleValue());
            return map;
        }).collect(Collectors.toList());
    }
}
