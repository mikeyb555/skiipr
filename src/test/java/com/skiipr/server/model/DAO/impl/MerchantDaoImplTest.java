package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class MerchantDaoImplTest {
    
    @Autowired
    private MerchantDao merchantDao;

    @Test
    public void testFindById(){
        Merchant merchant = merchantDao.findById(2l);
        Assert.assertEquals(merchant.getUsername(), "george");
    }
    
    @Test
    public void testFindByUsername(){
        Merchant merchant = merchantDao.findByUsername("fred");
        Long id = 1l;
        Assert.assertEquals(merchant.getMerchantID(), id); 
    }
    
    @Test
    public void testSave(){
        Merchant merchant = merchantDao.findByUsername("fred");
        merchant.setUsername("alex");
        merchant.setName("round bar");
        merchantDao.save(merchant);
        merchant = merchantDao.findByUsername("alex");
        Assert.assertEquals(merchant.getName(), "round bar");
    }
    
    @Test
    public void testUpdate(){
        Merchant merchant = merchantDao.findById(2l);
        merchant.setUsername("foobar");
        merchant.setAddressStreet("green street");
        merchantDao.update(merchant);
        merchant = merchantDao.findById(2l);
        Assert.assertEquals(merchant.getUsername(), "george");
        Assert.assertEquals(merchant.getAddressStreet(), "green street");
    }
    
    @Test
    public void testDelete(){
        Merchant merchant = merchantDao.findByUsername("fred");
        merchant.setUsername("bobby");
        merchantDao.save(merchant);
        merchant = merchantDao.findByUsername("bobby");
        merchantDao.delete(merchant);
        merchant = merchantDao.findByUsername("bobby");
        Assert.assertNull(merchant);
    }
}