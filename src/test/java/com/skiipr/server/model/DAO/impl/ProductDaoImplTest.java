/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Product;
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
public class ProductDaoImplTest {
    
    @Autowired
    private ProductDao productDao;
    
    
    @Test
    public void testFindAll(){
        List<Product> products = productDao.findAll();
        Product product1 = products.get(0);
        Assert.assertEquals(product1.getDescription(), "product1");
        Assert.assertEquals(products.size(), 2);
        
        
    }
    
    @Test
    public void testFindByCategoryID(){
        List<Product> products = productDao.findByCategoryID(1l);
        Assert.assertEquals(products.size(), 2);
        
    }
    
    @Test
    public void testFindByID(){
        Product product = productDao.findByID(2l);
        Assert.assertEquals(product.getDescription(), "product2");
        Category category = product.getCategory();
        Assert.assertEquals(category.getName(), "Basics");
    }
    
}
