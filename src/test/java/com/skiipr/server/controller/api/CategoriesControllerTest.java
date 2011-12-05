/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;


import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Michael
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class CategoriesControllerTest {
    
    @Mock
    Category category;
    
    @Mock
    CategoryDao categoryDao;
    
    @Mock
    List<Category> categoryList;
    
    @InjectMocks
    private CategoriesController controller;
    
    @Before
    public void setUpClass() throws Exception{
        controller = new CategoriesController();
        MockitoAnnotations.initMocks(this);
        Mockito.when(categoryDao.findByID(5l)).thenReturn(category);
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        
        
    }
    
    @Test
    public void testFindCategory(){
        Category returnCategory = controller.findMerchant(5l);
        Assert.assertEquals(category, returnCategory);
        Mockito.verify(categoryDao).findByID(5l);
    }
    
    @Test
    public void testFindAll(){
        List<Category> returnList = controller.findAll();
        Assert.assertEquals(categoryList, returnList);
        Mockito.verify(categoryDao).findAll();
    }
    
    
    
    
    
    
    
    
    
}
