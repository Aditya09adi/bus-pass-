package com.buspass.dto;

import java.time.LocalDate;

public class BusPassRequest {
    private Long userId;
    private String passType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String routeAccess;
    private String status;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRouteAccess() {
        return routeAccess;
    }

    public void setRouteAccess(String routeAccess) {
        this.routeAccess = routeAccess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}