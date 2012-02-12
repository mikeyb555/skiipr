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
import com.skiipr.server.model.form.ProductForm;
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
public class ProductControllerTest {

    @Mock 
    CategoryDao categoryDao;
    
    @Mock
    Category category;
    
    @Mock
    Product product;
    
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
    private ProductForm formProduct;
    
    private ArgumentCaptor<FlashNotification> flashArgument;
    
    @InjectMocks
    private ProductController controller;
    
    @Before
    public void setUpClass() throws Exception {
        controller = new ProductController();
        MockitoAnnotations.initMocks(this);
        
        flashArgument = ArgumentCaptor.forClass(FlashNotification.class);

        //modelMap
        Mockito.when(modelMap.addAttribute(Mockito.eq("flash"),  flashArgument.capture())).thenReturn(modelMap);
        
        //categoryDao
        Mockito.when(productDao.findRange(Mockito.anyInt(), Mockito.anyInt())).thenReturn(productList);
        Mockito.when(product.getCategory()).thenReturn(category);
        Mockito.when(productDao.countByMerchant()).thenReturn(20);
        Mockito.when(productDao.findByMerchant(5l)).thenReturn(product);
        Mockito.doNothing().when(productDao).update(product);
        Mockito.doNothing().when(productDao).save(Mockito.any(Product.class));
        
        //formProduct
        Mockito.when(formProduct.getProductID()).thenReturn(5l);
        Mockito.doNothing().when(formProduct).setAttributes(product);
        
        
        
        //sessionUser
        Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        
        //category
        Mockito.when(category.getCategoryID()).thenReturn(10l);
    }

    @Test
    public void testList() {
        Assert.assertEquals("/dashboard/products/list", controller.list(1, 10, modelMap));
        //Mockito.verify(modelMap, Mockito.times(3)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
        Mockito.verify(productDao).findRange(0, 10);
    }
    
   @Test
    public void testUpdate(){
        Mockito.when(formProduct.validate(productDao, bindingResult)).thenReturn(true);
        Assert.assertEquals("/dashboard/products/list", controller.update(1, 10, formProduct, bindingResult, modelMap));
        
        Mockito.verify(modelMap).addAttribute("openProdID", 5l);
        Mockito.verify(productDao).update(product);
        Mockito.verify(formProduct).setAttributes(product);
        Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
        
        Mockito.when(productDao.findByMerchant(5l)).thenReturn(null);
        Assert.assertEquals("/dashboard/products/list", controller.update(1, 10, formProduct, bindingResult, modelMap));
        Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());

        Mockito.when(productDao.findByMerchant(5l)).thenReturn(product);
        Mockito.when(formProduct.validate(productDao, bindingResult)).thenReturn(false);
        Assert.assertEquals("/dashboard/products/list", controller.update(1, 10, formProduct, bindingResult, modelMap));
        Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
    }
   
   @Test
   public void testDelete(){
       Assert.assertEquals("/dashboard/products/list", controller.delete(5l, modelMap, httpServletRequest));
       //Mockito.verify(modelMap, Mockito.times(4)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
       Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
       
       Mockito.when(productDao.findByMerchant(5l)).thenReturn(null);
       Assert.assertEquals("/dashboard/products/list", controller.delete(5l, modelMap, httpServletRequest));
       Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
       
       
       Mockito.when(productDao.findByMerchant(5l)).thenReturn(product);
       Assert.assertEquals("/dashboard/products/list", controller.delete(5l, modelMap, httpServletRequest));
       Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());     
   }
   
   @Test
   public void testCreate(){
       Assert.assertEquals("/dashboard/products/list", controller.create(formProduct, bindingResult, modelMap));
      //Mockito.verify(modelMap, Mockito.times(5)).addAttribute(Mockito.any(String.class), Mockito.anyObject());
       Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
       Mockito.verify(modelMap).addAttribute("openProdID", 0);
       
       Mockito.when(formProduct.validate(productDao, bindingResult)).thenReturn(true);
       Assert.assertEquals("/dashboard/products/list", controller.create(formProduct, bindingResult, modelMap));
       Mockito.verify(modelMap).addAttribute(Mockito.eq("openProdID"), Mockito.eq(null));
       Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
       Mockito.verify(productDao).save(Mockito.any(Product.class));
   }
}
