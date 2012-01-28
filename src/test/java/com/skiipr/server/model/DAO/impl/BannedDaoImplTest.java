package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Banned;
import com.skiipr.server.model.DAO.BannedDao;
import com.skiipr.server.model.LoginUser;
import java.util.ArrayList;
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
public class BannedDaoImplTest {

    @Mock
    SessionUser sessionUser;
    
    @Mock
    LoginUser loginUser;
    
    @InjectMocks
    @Autowired
    private BannedDao bannedDao;
    
    @Before
    public void setUpClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        //sessionUser
        Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        
        //loginUser
        Mockito.when(loginUser.getMerchantId()).thenReturn(3l);
    }
    
    @Test
    public void testSave(){
        Banned ban = new Banned();
        ban.setIdentifier("some@email.com");
        ban.setMerchantID(3l);
        bannedDao.save(ban);
        List<Banned> banned = bannedDao.findAll();
        Assert.assertEquals(1, banned.size());
        Mockito.verify(loginUser).getMerchantId();
    }
    
    @Test
    public void testUpdate(){
        Mockito.when(loginUser.getMerchantId()).thenReturn(1l);
        Banned ban = bannedDao.getBan(2l);
        ban.setIdentifier("another@email.com");
        bannedDao.update(ban);
        ban = bannedDao.getBan(2l);
        Assert.assertEquals("another@email.com", ban.getIdentifier());
        Mockito.verify(loginUser, Mockito.times(2)).getMerchantId();
    }
    
    @Test
    public void testDelete(){
        Mockito.when(loginUser.getMerchantId()).thenReturn(1l);
        Banned ban = bannedDao.getBan(1l);
        bannedDao.delete(ban);
        ban = bannedDao.getBan(1l);
        Assert.assertNull(ban);
    }
    
    @Test
    public void testIsBanned(){
        ArrayList<String> bannedIdentifiers = new ArrayList();
        bannedIdentifiers.add("random@foobar.com");
        bannedIdentifiers.add("notbanned@foobar.com");
        Assert.assertFalse(bannedDao.isBanned(bannedIdentifiers, 2l));
        bannedIdentifiers.add("test@foobar.com");
        Assert.assertTrue(bannedDao.isBanned(bannedIdentifiers, 2l));
        bannedIdentifiers.remove("test@foobar.com");
        Assert.assertFalse(bannedDao.isBanned(bannedIdentifiers, 2l));
        bannedIdentifiers.add("global@ban.com");
        Assert.assertTrue(bannedDao.isBanned(bannedIdentifiers, 2l));
    }
    
    @Test
    public void testFindAll(){
        Mockito.when(loginUser.getMerchantId()).thenReturn(2l);
        List<Banned> banned = bannedDao.findAll();
        Assert.assertEquals(1, banned.size());
    }
    
    @Test
    public void testGetBan(){
        Mockito.when(loginUser.getMerchantId()).thenReturn(2l);
        Banned ban = bannedDao.getBan(3l);
        Assert.assertEquals("test@foobar.com", ban.getIdentifier());
    }
    
    @Test
    public void testIsBannedSingle(){
        Mockito.when(loginUser.getMerchantId()).thenReturn(2l);
        Assert.assertFalse(bannedDao.isBanned("nothing@nothing.com"));
        Assert.assertTrue(bannedDao.isBanned("test@foobar.com"));
        Assert.assertTrue(bannedDao.isBanned("global@ban.com"));
    }
}
