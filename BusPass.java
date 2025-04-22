package com.buspass.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bus_passes")
public class BusPass {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "pass_id", unique = true, nullable = false)
    private String passId;
    @ManyToOne(fetch = FetchType.EAGER)

   
    
    
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "pass_type", nullable = false)
    private String passType;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "route_access", nullable = false)
    private String routeAccess;
    
    @Column(nullable = false)
    private String status = "Active";
    
    @Column(nullable = false)
    private double price;
    
    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;

        
    }
}