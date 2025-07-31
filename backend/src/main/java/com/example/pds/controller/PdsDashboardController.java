package com.example.pds.controller;

import com.example.pds.service.PdsDashboardService;
import com.example.pds.service.CustomUserDetails; // Import CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class PdsDashboardController {

    @Autowired
    private PdsDashboardService pdsDashboardService;

    // Helper method to get the raw user role from Authentication
    private String getUserRole(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUserRole();
        }
        return null;
    }

    // Helper method to get the district code from Authentication
    private String getUserDistCode(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getDistCode();
        }
        return null;
    }

    // Main dashboard metrics endpoint
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getDashboardMetrics(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        Map<String, Object> metrics = pdsDashboardService.getPdsDashboardMetrics(roleId, distCode);
        return ResponseEntity.ok(metrics);
    }

    // Details endpoints, now accepting Authentication to filter by role/district
    @GetMapping("/details/shops-by-district")
    public ResponseEntity<List<Map<String, Object>>> getShopsByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getShopsByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/mdm-by-district")
    public ResponseEntity<List<Map<String, Object>>> getMdmEntriesByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getMdmEntriesByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/icds-by-district")
    public ResponseEntity<List<Map<String, Object>>> getIcdsEntriesByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getIcdsEntriesByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/welfare-by-district")
    public ResponseEntity<List<Map<String, Object>>> getWelfareInstitutesByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getWelfareInstitutesByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/ro-generated-by-district")
    public ResponseEntity<List<Map<String, Object>>> getRoGeneratedByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getRoGeneratedByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/dispatched-by-district")
    public ResponseEntity<List<Map<String, Object>>> getDispatchedQtyByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getDispatchedQtyByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/details/received-by-district")
    public ResponseEntity<List<Map<String, Object>>> getReceivedQtyByDistrict(Authentication authentication) {
        String roleId = getUserRole(authentication);
        String distCode = getUserDistCode(authentication);
        List<Map<String, Object>> details = pdsDashboardService.getReceivedQtyByDistrict(roleId, distCode);
        return ResponseEntity.ok(details);
    }
}
