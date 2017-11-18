package com.airport.ticket.bms.entity;

import java.util.Date;

public class FlightMessage {
    private Integer id;

    private Date flightTime;

    private String company;

    private String origin;

    private String destination;

    private Integer seats;

    private Long flightPrice;

    private Boolean status;

    private String ext1;

    private Integer ext2;

    private Date insertTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Date flightTime) {
        this.flightTime = flightTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Long getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(Long flightPrice) {
        this.flightPrice = flightPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public Integer getExt2() {
        return ext2;
    }

    public void setExt2(Integer ext2) {
        this.ext2 = ext2;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}