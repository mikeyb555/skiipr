/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;

import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Product;
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
public class ProductsControllerTest {
    
    @Mock
    Product product;
    
    @Mock
    ProductDao productDao;
    
    @Mock
    List<Product> productList;
    
    @InjectMocks
    private ProductsController controller;
    
    @Before
    public void setUpClass() throws Exception{
        controller = new ProductsController();
        MockitoAnnotations.initMocks(this);
        Mockito.when(productDao.findByID(5l)).thenReturn(product);
        Mockito.when(productDao.findAll()).thenReturn(productList);
        
        
        
    }
    
    @Test
    public void testFindProduct(){
        Product returnProduct = controller.findProduct(5l);
        Assert.assertEquals(product, returnProduct);
        Mockito.verify(productDao).findByID(5l);
    }
    
    @Test
    public void testFindAll(){
        List<Product> returnList = controller.findAll();
        Assert.assertEquals(returnList, productList);
        Mockito.verify(productDao).findAll();
        
    }
    
    
}
