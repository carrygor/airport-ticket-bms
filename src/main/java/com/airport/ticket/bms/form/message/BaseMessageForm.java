package com.airport.ticket.bms.form.message;

import com.airport.ticket.bms.form.BaseForm;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class BaseMessageForm extends BaseForm implements Serializable{

    private int id;
    @NotNull
    private String company;
    @NotNull
    private String origin;
    @NotNull
    private String destination;
    @NotNull
    private Date flightTime;
    private int seats;
    private int residualSeats = 0;
    private long price;
    private boolean status = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResidualSeats() {
        return residualSeats;
    }

    public void setResidualSeats(int residualSeats) {
        this.residualSeats = residualSeats;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Date flightTime) {
        this.flightTime = flightTime;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
