package com.example.pds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_user", schema = "common")
public class LoginUser {

    @Id

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    // Default constructor
    public LoginUser() {
    }

    // Constructor with all fields (optional, for convenience)
    public LoginUser(String userId, String userRole, String distCode, String userName, String password, String status) {
        this.userId = userId;
        this.userRole = userRole;
        this.distCode = distCode;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
