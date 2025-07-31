package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "truck_challan_details", schema = "epos")
public class PdsTruckChallanDetail {

    @EmbeddedId
    private PdsTruckChallanDetailId id;

    @Column(name = "fps_id", length = 12)
    private String fpsId;

    @Column(name = "allotedmonth")
    private Integer allotedMonth;

    @Column(name = "allotedyear")
    private Integer allotedYear;

    @Column(name = "allocation_orderno", length = 42)
    private String allocationOrderNo;

    @Column(name = "comm_name", length = 30)
    private String commName;

    @Column(name = "scheme_name", length = 30)
    private String schemeName;

    @Column(name = "kra")
    private Double kra;

    @Column(name = "dispatched")
    private Double dispatched;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "ins_time")
    private LocalDateTime insTime;

    @Column(name = "received")
    private Double received;


    public PdsTruckChallanDetailId getId() {
        return id;
    }

    public void setId(PdsTruckChallanDetailId id) {
        this.id = id;
    }

    public String getFpsId() {
        return fpsId;
    }

    public void setFpsId(String fpsId) {
        this.fpsId = fpsId;
    }

    public Integer getAllotedMonth() {
        return allotedMonth;
    }

    public void setAllotedMonth(Integer allotedMonth) {
        this.allotedMonth = allotedMonth;
    }

    public Integer getAllotedYear() {
        return allotedYear;
    }

    public void setAllotedYear(Integer allotedYear) {
        this.allotedYear = allotedYear;
    }

    public String getAllocationOrderNo() {
        return allocationOrderNo;
    }

    public void setAllocationOrderNo(String allocationOrderNo) {
        this.allocationOrderNo = allocationOrderNo;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Double getKra() {
        return kra;
    }

    public void setKra(Double kra) {
        this.kra = kra;
    }

    public Double getDispatched() {
        return dispatched;
    }

    public void setDispatched(Double dispatched) {
        this.dispatched = dispatched;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInsTime() {
        return insTime;
    }

    public void setInsTime(LocalDateTime insTime) {
        this.insTime = insTime;
    }

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

}
