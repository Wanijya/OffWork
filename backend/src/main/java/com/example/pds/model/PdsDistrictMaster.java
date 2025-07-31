package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "district_master", schema = "common")
public class PdsDistrictMaster {
    @Id
    @Column(name = "dist_code", length = 3)
    private String distCode;

    @Column(name = "dist_name_en", length = 50, nullable = false)
    private String distNameEn;

    @Column(name = "dist_name_ll", length = 50)
    private String distNameLl;

    @Column(name = "state_code", length = 2, nullable = false)
    private String stateCode;

    public String getDistCode() {
        return distCode;
    }
    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getDistNameEn() {
        return distNameEn;
    }
    public void setDistNameEn(String distNameEn) {
        this.distNameEn = distNameEn;
    }

    public String getDistNameLl() {
        return distNameLl;
    }
    public void setDistNameLl(String distNameLl) {
        this.distNameLl = distNameLl;
    }

    public String getStateCode() {
        return stateCode;
    }
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }


}
