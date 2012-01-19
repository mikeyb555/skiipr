/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.enums.OrderType;
import com.skiipr.server.enums.PaymentType;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Order;
import java.util.List;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class OrderDaoImplTest {
    
    @Autowired
    private OrderDao orderDao;
    
    @Test
    public void testFindAll(){
        List<Order> orders = orderDao.findAll();
        Order order1 = orders.get(0);
        Assert.assertEquals(order1.getPaypalAddress(), "order1");
        Assert.assertEquals(orders.size(), 2);
        
        
    }
    
    @Test
    public void testFindByID(){
        Order order = orderDao.findByID(2l);
        Assert.assertEquals(order.getPaypalAddress(), "order2");
        
        
    }
    
    @Test
    public void testSave(){
        Order order = new Order();
        order.setLastUpdated(123l);
        order.setOrderTime(1234l);
        order.setOrderType(OrderType.NORMAL);
        order.setPaypalAddress("winning");
        order.setPaypalRef(1234l);
        order.setStatus(OrderStatus.COMPLETE);
        order.setEmail("test@test.com");
        order.setDeviceID("12345");
        order.setTotal(1234);
        order.setPaymentType(PaymentType.PAYPAL);
        order.setApid("1");
        Merchant merchant = new Merchant();
        merchant.setMerchantID(3l);
        order.setMerchant(merchant);
        orderDao.save(order);
        List<Order> orders = orderDao.findAll();
        Assert.assertEquals(orders.size(), 3);
        order = orders.get(2);
        Assert.assertEquals(order.getPaypalAddress(), "winning");
    }
    
    @Test
    public void testCount(){
        List<Order> orders = orderDao.findAllByMerchant(20l);
        Assert.assertEquals(orders.size(), 0);
        
        
    }
    
    
    
    
    
}
