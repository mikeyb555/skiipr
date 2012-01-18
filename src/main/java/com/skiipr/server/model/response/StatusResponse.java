package com.skiipr.server.model.response;

import com.skiipr.server.enums.OrderStatus;

public class StatusResponse {

    private Long orderID;
    private OrderStatus status;
    private Long lastUpdated;
    
    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
