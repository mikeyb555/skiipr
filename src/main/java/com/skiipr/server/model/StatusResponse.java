/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

/**
 *
 * @author Michael
 */
public class StatusResponse {

    /**
     * @return the orderID
     */
    public Long getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the status
     */
    public ResponseStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    /**
     * @return the lastUpdated
     */
    public Long getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public enum ResponseStatus{
        READY,
        NOTREADY
    }
    
    private Long orderID;
    private ResponseStatus status;
    private Long lastUpdated;
    
}
