package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "commodity_master", schema = "common")
public class PdsCommodityMaster {

    @Id
    @Column(name = "comm_id")
    private Integer commId;

    @Column(name = "comm_name_en", length = 30, nullable = false)
    private String commNameEn;

    @Column(name = "comm_name_ll", length = 30, nullable = false)
    private String commNameLl;

    @Column(name = "comm_short_name", length = 6)
    private String commShortName;

    @Column(name = "comm_measure_unit", length = 4, nullable = false)
    private String commMeasureUnit;

    @Column(name = "comm_type", length = 15, nullable = false)
    private String commType;

    @Column(name = "distribution_type", length = 1, nullable = false)
    private String distributionType;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "scm_active", nullable = false)
    private Integer scmActive;

    @Column(name = "scm_unit", length = 4)
    private String scmUnit;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @Column(name = "remarks", length = 30)
    private String remarks;

    // Getters and Setters
    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public String getCommNameEn() {
        return commNameEn;
    }

    public void setCommNameEn(String commNameEn) {
        this.commNameEn = commNameEn;
    }

    public String getCommNameLl() {
        return commNameLl;
    }

    public void setCommNameLl(String commNameLl) {
        this.commNameLl = commNameLl;
    }

    public String getCommShortName() {
        return commShortName;
    }

    public void setCommShortName(String commShortName) {
        this.commShortName = commShortName;
    }

    public String getCommMeasureUnit() {
        return commMeasureUnit;
    }

    public void setCommMeasureUnit(String commMeasureUnit) {
        this.commMeasureUnit = commMeasureUnit;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScmActive() {
        return scmActive;
    }

    public void setScmActive(Integer scmActive) {
        this.scmActive = scmActive;
    }

    public String getScmUnit() {
        return scmUnit;
    }

    public void setScmUnit(String scmUnit) {
        this.scmUnit = scmUnit;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
