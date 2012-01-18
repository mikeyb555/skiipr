package com.skiipr.server.model.response;

public class OrderResponse {

    public enum ResponseStatus{
        SUCCESS,
        ERROR
    }
    
    public enum ResponseErrors{
        NONE,
        TOTAL_MISMATCH,
        PRODUCT_INVALID,
        DETAILS_INVALID,
        SERVER_ERROR
    }
    
    private Long orderID;
    private ResponseStatus response;
    private ResponseErrors error;
    
    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public ResponseStatus getResponse() {
        return response;
    }

    public void setResponse(ResponseStatus response) {
        this.response = response;
    }

    public ResponseErrors getError() {
        return error;
    }

    public void setError(ResponseErrors error) {
        this.error = error;
    }
}
