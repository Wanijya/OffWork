package com.example.pds.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ro_reg", schema = "scm")
public class PdsRoReg {
    @Id
    @Column(name = "ro_no", length = 35)
    private String roNo;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "shop_no", length = 15)
    private String shopNo;

    @Column(name = "ro_date")
    private LocalDate roDate;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;


    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public LocalDate getRoDate() {
        return roDate;
    }

    public void setRoDate(LocalDate roDate) {
        this.roDate = roDate;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
