/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import com.skiipr.server.enums.CouponType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "tbl_coupon")
public class Coupon {
    
    @Id
    private Long couponID;
    private Long merchantID;
    private boolean active;
    private int couponCode;
    private CouponType couponType;
    private BigDecimal percentage;

    /**
     * @return the couponID
     */
    public Long getCouponID() {
        return couponID;
    }

    /**
     * @param couponID the couponID to set
     */
    public void setCouponID(Long couponID) {
        this.couponID = couponID;
    }

    /**
     * @return the merchantID
     */
    public Long getMerchantID() {
        return merchantID;
    }

    /**
     * @param merchantID the merchantID to set
     */
    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the couponCode
     */
    public int getCouponCode() {
        return couponCode;
    }

    /**
     * @param couponCode the couponCode to set
     */
    public void setCouponCode(int couponCode) {
        this.couponCode = couponCode;
    }

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
     * @return the percentage
     */
    public BigDecimal getPercentage() {
        return percentage;
    }

    /**
     * @param percentage the percentage to set
     */
    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
    
}
