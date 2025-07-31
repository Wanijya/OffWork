package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "icds_detail", schema = "epos")
public class PdsIcdsDetail {

    @Id
    @Column(name = "icds_id")
    private String icdsId;

    @Column(name = "name_of_the_school")
    private String nameOfTheSchool;

    @Column(name = "fps_id")
    private String fpsId;

    @Column(name = "dist_code", length = 4)
    private String distCode;

    @Column(name = "active", length = 1)
    private String active;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

    public String getIcdsId() {
        return icdsId;
    }

    public String getNameOfTheSchool() {
        return nameOfTheSchool;
    }

    public String getFpsId() {
        return fpsId;
    }

    public String getDistCode() {
        return distCode;
    }

    public String getActive() {
        return active;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setIcdsId(String icdsId) {
        this.icdsId = icdsId;
    }

    public void setNameOfTheSchool(String nameOfTheSchool) {
        this.nameOfTheSchool = nameOfTheSchool;
    }

    public void setFpsId(String fpsId) {
        this.fpsId = fpsId;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }
}
