/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Store Class
Description : Store class constructors, getters and setters.

Extension History:


*/

package com.example.newgen;

public class Store {
    private String createdBy;
    private String createdOn;
    private String schedule;
    private String scheduleDate;
    private String carriageNo;
    private String aircraftType;
    private String departurePort;
    private String arrivalPort;
    private String flightType;
    private String storeNr;
    private String storeInstanceNr;


    public Store() {
        super();
    }

    public Store(String createdBy, String createdOn, String schedule, String scheduleDate, String carriageNo,
                 String aircraftType, String departurePort, String arrivalPort, String flightType, String storeNr, String storeInstanceNr) {
        super();
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.schedule = schedule;
        this.scheduleDate = scheduleDate;
        this.carriageNo = carriageNo;
        this.aircraftType = aircraftType;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.flightType = flightType;
        this.storeNr = storeNr;
        this.storeInstanceNr = storeInstanceNr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getCarriageNo() {
        return carriageNo;
    }

    public void setCarriageNo(String carriageNo) {
        this.carriageNo = carriageNo;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    public String getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(String arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getStoreNr() {
        return storeNr;
    }

    public void setStoreNr(String storeNr) {
        this.storeNr = storeNr;
    }

    public String getStoreInstanceNr() {
        return storeInstanceNr;
    }

    public void setStoreInstanceNr(String storeInstanceNr) {
        this.storeInstanceNr = storeInstanceNr;
    }

    @Override
    public String toString() {
        return "Store [createdBy=" + createdBy + ", createdOn=" + createdOn + ", schedule=" + schedule
                + ", scheduleDate=" + scheduleDate + ", carriageNo=" + carriageNo + ", aircraftType=" + aircraftType
                + ", departurePort=" + departurePort + ", arrivalPort=" + arrivalPort + ", flightType=" + flightType
                + ", storeNr=" + storeNr + ", storeInstanceNr=" + storeInstanceNr + "]\n";
    }
}
