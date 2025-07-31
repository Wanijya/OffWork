package com.example.pds.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "fps_master", schema = "common")
public class PdsFpsMaster {
    @Id

    @Column(name = "fps_id", length = 12)
    private String fpsId;

    @Column(name = "afso_code", length = 7, nullable = false)
    private String afsoCode;

    @Column(name = "state_code", length = 2, nullable = false)
    private String stateCode;

    @Column(name = "dist_code", length = 4, nullable = false)
    private String distCode;

    @Column(name = "longtitude", length = 50, nullable = false)
    private String longitude;

    @Column(name = "latitude", length = 50, nullable = false)
    private String latitude;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @Column(name = "fps_status", length = 1, nullable = false)
    private String fpsStatus;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "depot_id", length = 10)
    private String depotId;

    @Column(name = "fps_name")
    private String fpsName;

    public String getFpsId() {
        return fpsId;
    }

    public void setFpsId(String fpsId) {
        this.fpsId = fpsId;
    }

    public String getAfsoCode() {
        return afsoCode;
    }

    public void setAfsoCode(String afsoCode) {
        this.afsoCode = afsoCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getFpsStatus() {
        return fpsStatus;
    }

    public void setFpsStatus(String fpsStatus) {
        this.fpsStatus = fpsStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }

    public String getFpsName() {
        return fpsName;
    }

    public void setFpsName(String fpsName) {
        this.fpsName = fpsName;
    }
}
