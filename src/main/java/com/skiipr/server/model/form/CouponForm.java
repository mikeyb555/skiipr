/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.form;

import com.skiipr.server.enums.CouponType;
import com.skiipr.server.model.Coupon;
import com.skiipr.server.model.DAO.CouponDao;
import java.math.BigDecimal;
import org.springframework.validation.Errors;

/**
 *
 * @author Michael
 */
public class CouponForm {
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
    
    public boolean validate(CouponDao couponDao, Errors errors){
        if (couponCode > 9999){
            errors.rejectValue("couponCode", "invalid.coupon.couponcode.incorrect");
        }
        
        return !errors.hasErrors();
            
    }
    
    public void getAttributes(Coupon coupon){
        couponID = coupon.getCouponID();
        merchantID= coupon.getMerchantID();
        active = coupon.getActive();
        couponCode = coupon.getCouponCode();
        couponType = coupon.getCouponType();
        percentage = coupon.getPercentage();
        
    }
    
    public void setAttributes(Coupon coupon){
        coupon.setCouponCode(couponCode);
        System.out.println("the coupon type is" + couponType);
        coupon.setCouponType(couponType);
        
        
        coupon.setPercentage(percentage);
        coupon.setActive(active);
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
            
    
    
    
}
