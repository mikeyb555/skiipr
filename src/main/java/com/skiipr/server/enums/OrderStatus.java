package com.skiipr.server.enums;

public enum OrderStatus {
    NEW("NEW"),
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    READY("READY"),
    COMPLETE("COMPLETE"),
    CANCELLED("CANCELLED"),
    ERROR("ERROR");
    
    private String dbValue;
    
    private OrderStatus(String dbValue){
        this.dbValue = dbValue;
    }
    
    public String getDBValue(){
        return dbValue;
    }
}
