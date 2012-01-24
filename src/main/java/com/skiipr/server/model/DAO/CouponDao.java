/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Coupon;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface CouponDao {
    public void save(Coupon coupon);
    public void update(Coupon coupon);
    public void delete(Coupon coupon);
    public Coupon findByCode(int couponCode, Long merchantID);
    public List<Coupon> findAllByMerchant();
     public Coupon findByIDandMerchant(Long id);
    
}
