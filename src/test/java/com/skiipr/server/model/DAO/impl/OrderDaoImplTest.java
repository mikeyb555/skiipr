/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.Order;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Michael
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class OrderDaoImplTest {
    
    @Autowired
    private OrderDao orderDao;
    
    @Test
    public void testFindAll(){
        List<Order> orders = orderDao.findByAll();
        Order order1 = orders.get(0);
        Assert.assertEquals(order1.getPaypalAddress(), "order1");
        Assert.assertEquals(orders.size(), 2);
        
        
    }
    
    @Test
    public void testFindByID(){
        Order order = orderDao.findByID(2l);
        Assert.assertEquals(order.getPaypalAddress(), "order2");
        
        
    }
    
    
    
}
