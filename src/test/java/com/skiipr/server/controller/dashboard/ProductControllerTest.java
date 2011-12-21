package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Product;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class ProductControllerTest {
    
    @Mock
    ProductDao productDao;
    
    @Mock
    CategoryDao categoryDao;
    
    @Mock
    Product product;
    
    @Mock
    Category category;
    
    @Mock
    Model model;
    
    @Mock
    List<Product> productList;
    
    @Mock
    List<Category> categoryList;
    
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
    private ProductController controller;
    
    @Before
    public void setUpClass() throws Exception{
        controller = new ProductController();
        MockitoAnnotations.initMocks(this);
        
        Mockito.when(productDao.findByID(3l)).thenReturn(product);
        Mockito.when(productDao.findByIDNoRelation(3l)).thenReturn(product);
        Mockito.when(productDao.findAll()).thenReturn(productList);
        Mockito.when(productDao.findRange(20, 5)).thenReturn(productList);
        Mockito.doNothing().when(productDao).update(product);
        Mockito.doNothing().when(productDao).save(product);
        Mockito.when(model.addAttribute("product", product)).thenReturn(model);
        Mockito.when(model.addAttribute("categories", categoryList)).thenReturn(model);
        Mockito.doNothing().when(map).clear();
        Mockito.when(model.asMap()).thenReturn(map);
        Mockito.when(product.getProductID()).thenReturn(3l);
        Mockito.doNothing().when(productDao).delete(product);
        Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        Mockito.when(category.getCategoryID()).thenReturn(1l);
        Mockito.when(product.getCategory()).thenReturn(category);
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        Mockito.when(categoryDao.findByMerchantId()).thenReturn(categoryList);
        Mockito.when(productDao.findByMerchant(3l)).thenReturn(product);
        Mockito.when(productDao.findAllByMerchant()).thenReturn(productList);
        Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        
        Mockito.when(loginUser.getMerchantId()).thenReturn(5l);
        
        
        Mockito.when(model.addAttribute(Mockito.contains("product"), Mockito.any(Product.class))).thenReturn(model);
        
        
        
    }
    
    
    @Test
    public void testShow() {
        Assert.assertEquals("/dashboard/products/view", controller.show(3l, model));
        Mockito.verify(productDao).findByMerchant(3l);
        Mockito.verify(model).addAttribute("product", product);
    }
    
     @Test
    public void testList() {
        Assert.assertEquals("/dashboard/products/list", controller.list(5, 5, model));
        Mockito.verify(productDao).findRange(20, 5);
    }
     
    
    
    @Test
    public void testUpdate() {
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Assert.assertEquals("/dashboard/products/update", controller.update(product, bindingResult, model, httpServletRequest));
        Mockito.verify(map).clear();
        Mockito.verify(productDao).update(product);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals("/dashboard/products/update", controller.update(product, bindingResult, model, httpServletRequest));
        Mockito.verify(model, Mockito.times(3)).addAttribute("product", product);
    }
    
    @Test
    public void testUpdateForm() {
        Assert.assertEquals("/dashboard/products/update", controller.updateForm(3l, model));
        Mockito.verify(productDao).findByMerchant(3l);
        Mockito.verify(model).addAttribute("product", product);
    }
    
    @Test
    public void testDelete() {
        Assert.assertEquals("/dashboard/products/list", controller.delete(3l, 2, 2, model));
        Mockito.verify(productDao).findByMerchant(3l);
        Mockito.verify(productDao).delete(product);
        Mockito.verify(map).clear();
    }
    
    @Test
    public void testCreate() {
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Assert.assertEquals("/dashboard/products/view", controller.create(product, bindingResult, model, httpServletRequest));
        Mockito.verify(map).clear();
        Mockito.verify(productDao).save(product);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals("/dashboard/products/create", controller.create(product, bindingResult, model, httpServletRequest));
        Mockito.verify(model, Mockito.times(2)).addAttribute("product", product);
        
        
    }
    
    @Test
    public void testCreateForm() {
        Assert.assertEquals("/dashboard/products/create", controller.createForm(model));
        Mockito.verify(model).addAttribute(Mockito.contains("product"), Mockito.any());
    }
    
    
    
    
    
    
     
   
     
     

     
    
     
     
     

    
    
    
    
            
            
    
}
