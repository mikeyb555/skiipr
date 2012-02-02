/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.DAO.OrderProductDao;
import com.skiipr.server.model.OrderProduct;
import com.skiipr.server.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("OrderProductDao")
public class OrderProductDaoImpl extends HibernateDaoSupport implements OrderProductDao {
     @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }
    
    @Override
    public void save(OrderProduct orderProduct) {
        getHibernateTemplate().save(orderProduct);
    }

    @Override
    public void update(OrderProduct orderProduct) {
        getHibernateTemplate().update(orderProduct);
    }

    @Override
    public void delete(OrderProduct orderProduct) {
        getHibernateTemplate().delete(orderProduct);
    }

    @Override
    public List<OrderProduct> findByOrderID(Long id) {
        System.out.println("hello world");
        List list = getHibernateTemplate().find("from OrderProduct where userOrder.orderID=?", id);
        
        
        if(list.isEmpty()){
            return null;
        }
 
        
        return list;
	
    }
    
    
    
}
