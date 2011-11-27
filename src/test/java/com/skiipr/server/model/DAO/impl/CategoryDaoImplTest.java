/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 *
 * @author Michael
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class CategoryDaoImplTest {
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Test
    public void testFindAll(){
        List<Category> categories = categoryDao.findAll();
        assertEquals(categories.size(), 3);
    }
    
    @Test
    public void testFindById(){
        Category category = categoryDao.findByID(1l);
        Assert.assertEquals(category.getName(), "Basics");
        
        
    }
    
    @Test
    public void testFindByMerchantId(){
        List<Category> categories = categoryDao.findByMerchantId(1l);
        Assert.assertEquals(categories.size(), 2);
        
    }
    
    
}
