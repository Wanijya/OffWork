package com.example.pds.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "mst_welfare_institute", schema = "scm")
public class PdsMstWelfareInstitute {
    @Id
    @Column(name = "institute_id", length = 8)
    private String instituteId;

    @Column(name = "institute_name", length = 100)
    private String instituteName;

    @Column(name = "district_code", length = 4)
    private String districtCode;

    @Column(name = "active")
    private Integer active;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;


    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
