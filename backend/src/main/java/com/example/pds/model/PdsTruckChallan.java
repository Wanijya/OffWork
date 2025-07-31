package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "truck_challan", schema = "epos")
public class PdsTruckChallan {
    @Id
    @Column(name = "truckchitno", length = 42)
    private String truckChitNo;

    @Column(name = "fps_id", length = 12, nullable = false)
    private String fpsId;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "statecode")
    private String stateCode;

    @Column(name = "entrydate")
    private LocalDate entryDate;

    @Column(name = "allocation_orderno", length = 42)
    private String allocationOrderNo;

    @Column(name = "challanid", length = 40)
    private String challanId;

    @Column(name = "truckno", length = 30)
    private String truckNo;

    @Column(name = "noofcomm", length = 2)
    private String noOfComm;

    @Column(name = "allotedmonth", nullable = false)
    private Integer allotedMonth;

    @Column(name = "allotedyear", nullable = false)
    private Integer allotedYear;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "ins_time")
    private LocalDateTime insTime;

    @Column(name = "req_id")
    private String reqId;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "depot_id", length = 10)
    private String depotId;

    @Column(name = "godown_id", length = 16)
    private String godownId;

    @Column(name = "reach_status")
    private Integer reachStatus;

    @Column(name = "reach_time")
    private LocalDateTime reachTime;

    @Column(name = "driver_mobile_no", length = 10)
    private String driverMobileNo;

    @Column(name = "driver_aadhaar_no", length = 12)
    private String driverAadhaarNo;

    @Column(name = "rec_fps_id", length = 12)
    private String recFpsId;

    @Column(name = "afso_code", length = 7)
    private String afsoCode;

    public String getTruckChitNo() {
        return truckChitNo;
    }

    public void setTruckChitNo(String truckChitNo) {
        this.truckChitNo = truckChitNo;
    }

    public String getFpsId() {
        return fpsId;
    }

    public void setFpsId(String fpsId) {
        this.fpsId = fpsId;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getAllocationOrderNo() {
        return allocationOrderNo;
    }

    public void setAllocationOrderNo(String allocationOrderNo) {
        this.allocationOrderNo = allocationOrderNo;
    }

    public String getChallanId() {
        return challanId;
    }

    public void setChallanId(String challanId) {
        this.challanId = challanId;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getNoOfComm() {
        return noOfComm;
    }

    public void setNoOfComm(String noOfComm) {
        this.noOfComm = noOfComm;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getInsTime() {
        return insTime;
    }

    public void setInsTime(LocalDateTime insTime) {
        this.insTime = insTime;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }

    public String getGodownId() {
        return godownId;
    }

    public void setGodownId(String godownId) {
        this.godownId = godownId;
    }

    public Integer getReachStatus() {
        return reachStatus;
    }

    public void setReachStatus(Integer reachStatus) {
        this.reachStatus = reachStatus;
    }

    public LocalDateTime getReachTime() {
        return reachTime;
    }

    public void setReachTime(LocalDateTime reachTime) {
        this.reachTime = reachTime;
    }

    public String getDriverMobileNo() {
        return driverMobileNo;
    }

    public void setDriverMobileNo(String driverMobileNo) {
        this.driverMobileNo = driverMobileNo;
    }

    public String getDriverAadhaarNo() {
        return driverAadhaarNo;
    }

    public void setDriverAadhaarNo(String driverAadhaarNo) {
        this.driverAadhaarNo = driverAadhaarNo;
    }

    public String getRecFpsId() {
        return recFpsId;
    }

    public void setRecFpsId(String recFpsId) {
        this.recFpsId = recFpsId;
    }

    public String getAfsoCode() {
        return afsoCode;
    }

    public void setAfsoCode(String afsoCode) {
        this.afsoCode = afsoCode;
    }
}
