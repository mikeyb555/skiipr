/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.SessionUser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;
import org.mockito.MockitoAnnotations;
import com.skiipr.server.model.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Product;
import java.util.Map;
import org.springframework.ui.Model;
import junit.framework.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class CategoryControllerTest {

    @Mock 
    CategoryDao categoryDao;
    
    @Mock
    Category category;
    
    @Mock
    ProductDao productDao;
    
    @Mock
    Model model;
    
    @Mock
    List<Category> categoryList;
    
    @Mock
    List<Product> productList;
    
    @Mock
    BindingResult bindingResult;
    
    @Mock
    HttpServletRequest httpServletRequest;
    
    @Mock
    Map map;
    
    @Mock
    LoginUser loginUser;
    
    @Mock
    private SessionUser sessionUser;
    
    @InjectMocks
    private CategoryController controller;
    
    @Before
    public void setUpClass() throws Exception {
        controller = new CategoryController();
        MockitoAnnotations.initMocks(this);
        Mockito.when(categoryDao.findByID(3l)).thenReturn(category);
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        Mockito.when(categoryDao.findByMerchantId(5l)).thenReturn(categoryList);
        Mockito.when(categoryDao.findCategoryByMerchantId(3l)).thenReturn(category);
        Mockito.when(categoryDao.findByMerchantId()).thenReturn(categoryList);
        Mockito.when(productDao.findByCategoryID(3l)).thenReturn(productList);
        Mockito.when(productList.isEmpty()).thenReturn(false);
        Mockito.doNothing().when(categoryDao).update(category);
        Mockito.doNothing().when(categoryDao).save(category);
        Mockito.when(model.addAttribute("category", category)).thenReturn(model);
        Mockito.doNothing().when(map).clear();
        Mockito.when(model.asMap()).thenReturn(map);
        Mockito.when(category.getCategoryID()).thenReturn(3l);
        Mockito.doNothing().when(categoryDao).delete(category);
        Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        Mockito.when(loginUser.getMerchantId()).thenReturn(5l);
        Mockito.doNothing().when(category).setMerchantID(5l);
        Mockito.when(model.addAttribute(Mockito.contains("category"), Mockito.any(Category.class))).thenReturn(model);
    }

    @Test
    public void testShow() {
        Assert.assertEquals("/dashboard/categories/view", controller.show(3l, model));
        Mockito.verify(model).addAttribute("category", category);
        Mockito.verify(categoryDao).findCategoryByMerchantId(3l);
    }

    @Test
    @Ignore
    public void testList() {
        //Assert.assertEquals("/dashboard/categories/list", controller.list(5, 5, model));
        Mockito.verify(categoryDao, Mockito.times(2)).findByMerchantId();
    }

    @Test
    public void testUpdate() {
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        //Assert.assertEquals("/dashboard/categories/update", controller.update(category, bindingResult, model, httpServletRequest));
        Mockito.verify(map).clear();
        Mockito.verify(categoryDao).update(category);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        //Assert.assertEquals("/dashboard/categories/edit", controller.update(category, bindingResult, model, httpServletRequest));
        Mockito.verify(model, Mockito.times(2)).addAttribute("category", category);
    }

    @Test
    public void testUpdateForm() {
        Assert.assertEquals("/dashboard/categories/update", controller.updateForm(3l, model));
        
        Mockito.verify(categoryDao).findCategoryByMerchantId(3l);
        Mockito.verify(model).addAttribute("category", category);
    }

    @Test
    public void testDelete() {
        Assert.assertEquals("/dashboard/categories/list", controller.delete(3l, 2, 2, model));
        Mockito.verify(categoryDao).findCategoryByMerchantId(3l);
        Mockito.verify(productDao).findByCategoryID(3l);
        Mockito.verify(productList).isEmpty();
        Mockito.verify(categoryDao).delete(category);
        Mockito.verify(map).clear();
    }

    @Test
    public void testCreate() {
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Assert.assertEquals("/dashboard/categories/view", controller.create(category, bindingResult, model, httpServletRequest));
        Mockito.verify(map).clear();
        Mockito.verify(categoryDao).save(category);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals("/dashboard/categories/create", controller.create(category, bindingResult, model, httpServletRequest));
        Mockito.verify(model, Mockito.times(2)).addAttribute("category", category);
        Mockito.verify(category).setMerchantID(5l);
        Mockito.verify(loginUser).getMerchantId();
    }

    @Test
    public void testCreateForm() {
        Assert.assertEquals("/dashboard/categories/create", controller.createForm(model));
        Mockito.verify(model).addAttribute(Mockito.contains("category"), Mockito.any());
    }
}
