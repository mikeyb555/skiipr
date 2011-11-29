/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnit44Runner;
import com.skiipr.server.model.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.skiipr.server.model.DAO.CategoryDao;
import java.util.Collection;
import java.util.Map;
import org.springframework.ui.Model;
import junit.framework.Assert;
import org.springframework.ui.ModelMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

@RunWith(MockitoJUnit44Runner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class CategoryControllerTest {

    @Mock 
    CategoryDao categoryDao;
    
    @InjectMocks
    private CategoryController controller;
    
    @Before
    public void setUpClass() throws Exception {
        controller = new CategoryController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShow() {
        Category category = Mockito.mock(Category.class);
        if(categoryDao == null){
            System.out.println("CategoryDao null");
        }
        Mockito.when(categoryDao.findByID(3l)).thenReturn(category);
        Model model = Mockito.mock(Model.class);
        Mockito.when(model.addAttribute("category", category)).thenReturn(model);
        Assert.assertEquals("/dashboard/categories/view", controller.show(3l, model));
        Mockito.verify(categoryDao).findByID(3l);
        Mockito.verify(model).addAttribute("category", category);
    }

    @Test
    public void testList() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testUpdateForm() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testCreate() {
    }

    @Test
    public void testCreateForm() {
    }
}
