package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PdsTruckChallanDetailId implements Serializable {
    @Column(name = "truckchitno", length = 42)
    private String truckChitNo;

    @Column(name = "comm_code")
    private Integer commCode;

    @Column(name = "scheme_id")
    private Integer schemeId;

    // Default constructor
    public PdsTruckChallanDetailId() {}

    public PdsTruckChallanDetailId(String truckChitNo, Integer commCode, Integer schemeId) {
        this.truckChitNo = truckChitNo;
        this.commCode = commCode;
        this.schemeId = schemeId;
    }


    public String getTruckChitNo() {
        return truckChitNo;
    }

    public void setTruckChitNo(String truckChitNo) {
        this.truckChitNo = truckChitNo;
    }

    public Integer getCommCode() {
        return commCode;
    }

    public void setCommCode(Integer commCode) {
        this.commCode = commCode;
    }

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }

    // Equals and HashCode are crucial for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdsTruckChallanDetailId that = (PdsTruckChallanDetailId) o;
        return Objects.equals(truckChitNo, that.truckChitNo) &&
                Objects.equals(commCode, that.commCode) &&
                Objects.equals(schemeId, that.schemeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(truckChitNo, commCode, schemeId);
    }
}
