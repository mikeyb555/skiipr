/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
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
import com.skiipr.server.model.form.CategoryForm;
import java.util.Map;
import junit.framework.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

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
    ModelMap modelMap;
    
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
    SessionUser sessionUser;
    
    @Mock
    private CategoryForm formCategory;
    
    private ArgumentCaptor<FlashNotification> flashArgument;
    
    @InjectMocks
    private CategoryController controller;
    
    @Before
    public void setUpClass() throws Exception {
        controller = new CategoryController();
        MockitoAnnotations.initMocks(this);
        
        flashArgument = ArgumentCaptor.forClass(FlashNotification.class);

        //modelMap
        Mockito.when(modelMap.addAttribute(Mockito.eq("flash"),  flashArgument.capture())).thenReturn(modelMap);
        
        //categoryDao
        Mockito.when(categoryDao.findRange(Mockito.anyInt(), Mockito.anyInt())).thenReturn(categoryList);
        Mockito.when(categoryDao.countByMerchant()).thenReturn(20);
        Mockito.when(categoryDao.findCategoryByMerchantId(5l)).thenReturn(category);
        Mockito.doNothing().when(categoryDao).update(category);
        Mockito.doNothing().when(categoryDao).save(Mockito.any(Category.class));
        
        //formCategory
        Mockito.when(formCategory.getCategoryID()).thenReturn(5l);
        Mockito.doNothing().when(formCategory).setAttributes(category);
        
        //productList
        Mockito.when(productList.isEmpty()).thenReturn(false);
        
        //sessionUser
        Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        
        //category
        Mockito.when(category.getCategoryID()).thenReturn(10l);
    }

    @Test
    public void testList() {
        Assert.assertEquals("/dashboard/categories/list", controller.list(1, 10, modelMap));
        Mockito.verify(modelMap, Mockito.times(3)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
        Mockito.verify(categoryDao).findRange(0, 10);
    }
    
   @Test
    public void testUpdate(){
        Mockito.when(formCategory.validate(categoryDao, bindingResult)).thenReturn(true);
        Assert.assertEquals("/dashboard/categories/list", controller.update(1, 10, formCategory, bindingResult, modelMap));
        Mockito.verify(modelMap, Mockito.times(5)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
        Mockito.verify(modelMap).addAttribute("openCatID", 5l);
        Mockito.verify(categoryDao).update(category);
        Mockito.verify(formCategory).setAttributes(category);
        Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
        
        Mockito.when(categoryDao.findCategoryByMerchantId(5l)).thenReturn(null);
        Assert.assertEquals("/dashboard/categories/list", controller.update(1, 10, formCategory, bindingResult, modelMap));
        Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());

        Mockito.when(categoryDao.findCategoryByMerchantId(5l)).thenReturn(category);
        Mockito.when(formCategory.validate(categoryDao, bindingResult)).thenReturn(false);
        Assert.assertEquals("/dashboard/categories/list", controller.update(1, 10, formCategory, bindingResult, modelMap));
        Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
    }
   
   @Test
   public void testDelete(){
       Assert.assertEquals("/dashboard/categories/list", controller.delete(5l, modelMap, httpServletRequest));
       Mockito.verify(modelMap, Mockito.times(4)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
       Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
       
       Mockito.when(categoryDao.findCategoryByMerchantId(5l)).thenReturn(null);
       Assert.assertEquals("/dashboard/categories/list", controller.delete(5l, modelMap, httpServletRequest));
       Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
       
       Mockito.when(productDao.findByCategoryID(5l)).thenReturn(productList);
       Mockito.when(categoryDao.findCategoryByMerchantId(5l)).thenReturn(category);
       Assert.assertEquals("/dashboard/categories/list", controller.delete(5l, modelMap, httpServletRequest));
       Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());     
   }
   
   @Test
   public void testCreate(){
       Assert.assertEquals("/dashboard/categories/list", controller.create(formCategory, bindingResult, modelMap));
       Mockito.verify(modelMap, Mockito.times(5)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
       Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
       Mockito.verify(modelMap).addAttribute("openCatID", 0);
       
       Mockito.when(formCategory.validate(categoryDao, bindingResult)).thenReturn(true);
       Assert.assertEquals("/dashboard/categories/list", controller.create(formCategory, bindingResult, modelMap));
       Mockito.verify(modelMap).addAttribute(Mockito.eq("openCatID"), Mockito.eq(null));
       Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
       Mockito.verify(categoryDao).save(Mockito.any(Category.class));
   }
}
