/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.response;

/**
 *
 * @author Michael
 */
public class BannedResponse {
    
public enum ResponseStatus{
        SUCCESS,
        ERROR
    }
    
    public enum ResponseErrors{
        NONE,
        TOTAL_MISMATCH,
        PRODUCT_INVALID,
        DETAILS_INVALID,
        SERVER_ERROR,
        INVALID_EMAIL,
        BLOCKED,
        MERCHANT_CLOSED,
        PAYMENT_UNSUPPORTED
    }
    
    private Long bannedID;
    private ResponseStatus response;
    private ResponseErrors error;
    
    public Long getBannedID() {
        return bannedID;
    }

    public void setBannedID(Long bannedID) {
        this.bannedID = bannedID;
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
