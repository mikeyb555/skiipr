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
    
    @Test
    public void testSave(){
        Category category = new Category();
        category.setName("Beer");
        category.setMerchantID(2l);
        categoryDao.save(category);
        List<Category> categories = categoryDao.findAll();
        assertEquals(categories.size(), 4);
        category = categories.get(3);
        Assert.assertEquals(category.getName(), "Beer");
        
    }
    
    @Test
    public void testUpdate(){
        Category category = categoryDao.findByID(2l);
        Assert.assertEquals(category.getName(), "Cocktails");
        category.setName("Tapas");
        categoryDao.update(category);
        category = categoryDao.findByID(2l);
        Assert.assertEquals(category.getName(), "Tapas");
    }
    
    @Test
    public void testDelete(){
        Long savedId;
        Category category = categoryDao.findByID(3l);
        category.setName("nobugs");
        categoryDao.save(category);
        savedId = category.getCategoryID();
        category = categoryDao.findByID(savedId);
        Assert.assertEquals(category.getName(), "nobugs");
        categoryDao.delete(category);
        category = categoryDao.findByID(savedId);
        Assert.assertNull(category);
    }
    
        
    
    
}
