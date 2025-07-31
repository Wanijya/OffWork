package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "mdm_detail", schema = "epos")
public class PdsMdmDetail {
    @Id
    @Column(name = "school_id")
    private String schoolId;

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


    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getNameOfTheSchool() {
        return nameOfTheSchool;
    }

    public void setNameOfTheSchool(String nameOfTheSchool) {
        this.nameOfTheSchool = nameOfTheSchool;
    }

    public String getFpsId() {
        return fpsId;
    }

    public void setFpsId(String fpsId) {
        this.fpsId = fpsId;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }
}
