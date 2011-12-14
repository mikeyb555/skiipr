package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.LoginUser;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class ProductDaoImplTest {
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
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
        Assert.assertEquals(products.size(), 3);
        
    }
    
    @Test
    public void testFindByID(){
        Product product = productDao.findByID(2l);
        Assert.assertEquals(product.getDescription(), "hello_world");
        Category category = product.getCategory();
        Assert.assertEquals(category.getName(), "Basics");
    }
    
    @Test
    public void testSave(){
        
        Product product = new Product();
        product.setDescription("foobar");
        product.setActive(true);
        product.setCategory(categoryDao.findByID(1l));
        product.setPrice(1);
        productDao.save(product);
        List<Product> products = productDao.findAll();
        Assert.assertEquals(products.size(), 3);
        product = products.get(2);
        Assert.assertEquals(product.getDescription(), "foobar");
        
    }
    
    
    @Test
    public void testUpdate(){
        Product product = productDao.findByIDNoRelation(2l);
        Assert.assertEquals(product.getDescription(), "product2");
        product.setDescription("hello_world");
        productDao.update(product);
        product = productDao.findByIDNoRelation(2l);
        Assert.assertEquals(product.getDescription(), "hello_world");
    }
    
    
    @Test
    public void testDelete(){
        Long savedId;
        Product product = productDao.findByIDNoRelation(2l);
        product.setDescription("nobugs");
        productDao.save(product);
        savedId = product.getProductID();
        product = productDao.findByIDNoRelation(savedId);
        Assert.assertEquals(product.getDescription(), "nobugs");
        productDao.delete(product);
        product= productDao.findByIDNoRelation(savedId);
        Assert.assertNull(product);
    }
    
    @Test
    public void testFindByMerchant(){
        Product product = productDao.findByMerchant(2l, 1l);
        Assert.assertEquals(product.getDescription(), "product2");
        product = productDao.findByMerchant(2l, 2l);
        Assert.assertNull(product);
    }
    
}
