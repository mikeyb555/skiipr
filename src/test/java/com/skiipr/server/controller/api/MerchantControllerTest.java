/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import java.util.List;
import junit.framework.Assert;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class MerchantControllerTest {

    @Mock 
    Merchant merchant;
    
    @Mock
    MerchantDao merchantDao;
    
    @Mock
    List<Merchant> merchantList;
    
    @InjectMocks
    private MerchantController controller;
    
    @Before
    public void setUpClass() throws Exception {
        controller = new MerchantController();
        MockitoAnnotations.initMocks(this);
        Mockito.when(merchantDao.findById(5l)).thenReturn(merchant);
        Mockito.when(merchantDao.findAll()).thenReturn(merchantList);
        Mockito.when(merchantDao.findByName("foobar")).thenReturn(merchantList);
    }
    
    @Test
    public void testFindMerchant() {
        Merchant returnMerchant = controller.findMerchant(5l);
        Assert.assertEquals(merchant, returnMerchant);
        Mockito.verify(merchantDao).findById(5l);
    }

    @Test
    public void testFindAll() {
        List<Merchant> returnList = controller.findAll();
        Assert.assertEquals(merchantList, returnList);
        Mockito.verify(merchantDao).findAll();
    }
    
    @Test
    public void testFindByName(){
        List<Merchant> returnList = controller.findByName("foobar");
        Assert.assertEquals(merchantList, returnList);
        Mockito.verify(merchantDao).findByName("foobar");
    }
}
