/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.response;

import com.skiipr.server.enums.CouponType;
import java.math.BigDecimal;

/**
 *
 * @author Michael
 */
public class CouponResponse {
    
     public enum ResponseStatus{
        VALID,
        INVALID
    }
    
    private CouponType couponType;
    private ResponseStatus response;
    private BigDecimal value;

    /**
     * @return the couponType
     */
    public CouponType getCouponType() {
        return couponType;
    }

    /**
     * @param couponType the couponType to set
     */
    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    /**
     * @return the response
     */
    public ResponseStatus getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(ResponseStatus response) {
        this.response = response;
    }

    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    
   
    
    
    
    
    
}
