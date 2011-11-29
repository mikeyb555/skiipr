/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.DAO.OrderDao;

import com.skiipr.server.model.Order;
import com.skiipr.server.model.Plan;
import java.util.List;
import org.hibernate.SessionFactory;
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
    public List<Order> findByAll() {
        List list = getHibernateTemplate().find("from Order");
        if(list.isEmpty()){
            return null;
        }
	return (List<Order>) list;
    }

}
    
