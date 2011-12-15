/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Tim
 */
public class TestOrder {
    
    
    @Test
    public void testOrder(){
        Order order = new Order();
        order.setLastUpdated(3l);
        Assert.assertEquals(order.getLastUpdated().toString(), "3");
    }
    
}
