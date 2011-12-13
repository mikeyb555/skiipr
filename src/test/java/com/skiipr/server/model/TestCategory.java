/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Michael
 */
public class TestCategory {
    
    
    
    @Test
    public void testName(){
        long merchantid = 5l;
        Category category = new Category();
        category.setName("test category");
        category.setCategoryID(1l);
        category.setMerchantID(5l);
        Assert.assertEquals(category.getName(), "test category");
        
        
        
        
        
    }
    
    
    
    
    
    
}
