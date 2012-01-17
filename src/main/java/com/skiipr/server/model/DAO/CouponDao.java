/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Coupon;

/**
 *
 * @author Michael
 */
public interface CouponDao {
    public Coupon findByCode(int couponCode, Long merchantID);
    
}
