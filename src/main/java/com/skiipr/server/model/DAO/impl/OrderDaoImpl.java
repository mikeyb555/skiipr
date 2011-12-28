/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.DAO.OrderDao;

import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.Plan;
import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("OrderDao")
public class OrderDaoImpl extends HibernateDaoSupport implements OrderDao {
    
    @Autowired
    SessionUser sessionUser;

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }
    
    @Override
    public void save(Order order) {
        getHibernateTemplate().save(order);
    }

    @Override
    public void update(Order order) {
        getHibernateTemplate().update(order);
    }

    @Override
    public void delete(Order order) {
        getHibernateTemplate().delete(order);
    }

    @Override
    public Order findByID(Long id) {
        List list = getHibernateTemplate().find("from Order where orderID=?", id);
        if(list.isEmpty()){
            return null;
        }
	return (Order)list.get(0);
    }

    @Override
    public List<Order> findAll() {
        
        List list = getHibernateTemplate().find("from Order");
        if(list.isEmpty()){
            return null;
        }
	return (List<Order>) list;
    }
    
    @Override
    public List<Order> findAllByMerchant(){
        LoginUser user = sessionUser.getUser();
        return findAllByMerchant(user.getMerchantId());
        
        
    }
    
    @Override
    public List<Order> findAllByMerchant(Long merchantID){
        List<Order> orders = getHibernateTemplate().find("from Order where merchant.merchantID = ?", merchantID);
        if (orders.isEmpty()){
            return Collections.EMPTY_LIST;
        }else{
            return (List<Order>) orders;
        }
        
    }
    
    @Override
    public Order findOrderByMerchant(Long orderID){
        LoginUser user = sessionUser.getUser();
        return findOrderByMerchant(orderID, user.getMerchantId());
    }
    
    @Override
    public Order findOrderByMerchant(Long orderID, Long merchantID){
        String[] params = {"ordID", "merchID"};
        Object[] values = {orderID, merchantID};
        List<Order> orders = getHibernateTemplate().findByNamedParam("from Order where (orderID = :ordID) AND merchant.merchantID = :merchID)", params, values);
        if(orders.isEmpty()){
            return null;
        }
	return (Order) orders.get(0);
    }
    
    @Override
    public List<Order> findRange(Integer first, Integer max) {
        Long merchantID = sessionUser.getUser().getMerchantId();
        Criteria criteria = getSession().createCriteria(Order.class)
                .setMaxResults(max)
                .setFirstResult(first)
                .add(Restrictions.eq("merchant.merchantID", merchantID));
        List<Order> categories = criteria.list();
        System.out.println("Size:" + categories.size());
        System.out.println("Start: " + first);
        System.out.println("Max: " + max);
        if(categories.isEmpty()){
            return null;
        }
	return categories;
    }
    
    @Override
    public Integer countByMerchant(){
        return findAllByMerchant().size();
        
            
        
    }
    
    
    

}
    

