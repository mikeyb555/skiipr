/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;


import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Coupon;
import com.skiipr.server.model.DAO.CouponDao;

import com.skiipr.server.model.LoginUser;
import java.util.Collections;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("CouponDao")
public class CouponDaoImpl extends HibernateDaoSupport implements CouponDao {
    
    @Autowired
    SessionUser sessionUser;
    
    @Autowired
    public void init(SessionFactory factory){
        setSessionFactory(factory);
    }
    
    @Override
    public void save(Coupon coupon) {
        getHibernateTemplate().save(coupon);
    }

    @Override
    public void update(Coupon coupon) {
        getHibernateTemplate().update(coupon);
    }

    @Override
    public void delete(Coupon coupon) {
        getHibernateTemplate().delete(coupon);
    }
    
    @Override
    public Coupon findByIDandMerchant(Long id){
        LoginUser user = sessionUser.getUser();
        String[] params = {"coupID", "merchID"};
        Object[] values = {id, user.getMerchantId()};
        List list = getHibernateTemplate().findByNamedParam("from Coupon where (couponID = :coupID) AND  (merchantID = :merchID)", params, values);
        if(list.isEmpty()){
            return null;
        }
	return (Coupon)list.get(0);
    }
    
    @Override
    public Coupon findByCode(int couponCode){
        LoginUser user = sessionUser.getUser();
        return findByCode(couponCode, user.getMerchantId());
    }
    

    @Override
    public Coupon findByCode(int couponCode, Long merchantID) {
        
        String[] params = {"couponC", "merchID"};
        Object[] values = {couponCode, merchantID};
        List list = getHibernateTemplate().findByNamedParam("from Coupon where (couponCode = :couponC) AND  (merchantID = :merchID)", params, values);
        if(list.isEmpty()){
            return null;
        }
	return (Coupon)list.get(0);
        
        
        
        
  
        
        
    }
    
    @Override
    public List<Coupon> findAllByMerchant(){
        LoginUser user = sessionUser.getUser();
        List<Coupon> coupons = getHibernateTemplate().find("from Coupon where merchantID = ?", user.getMerchantId());
        if (coupons.isEmpty()){
            return Collections.EMPTY_LIST;
        }else{
            return (List<Coupon>) coupons;
        }
        
    }
    
    
}
