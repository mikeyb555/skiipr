package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.form.AccountForm;
import javax.servlet.http.HttpServletRequest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class AccountControllerTest {
    
    @Mock
    SessionUser sessionUser;
    
    @Mock
    MerchantDao merchantDao;
    
    @Mock
    ModelMap modelMap;
    
    @Mock
    Merchant merchant;
    
    @Mock
    BindingResult bindingResult;
    
    @Mock
    HttpServletRequest httpServletRequest;
    
    @Mock
    AccountForm accountForm;
    
    @InjectMocks
    private AccountController controller;
    
    private ArgumentCaptor<FlashNotification> flashArgument;
    
    @Before
    public void setUpClass() throws Exception {
        controller = new AccountController();
        MockitoAnnotations.initMocks(this);
        flashArgument = ArgumentCaptor.forClass(FlashNotification.class);
        
        //modelMap
        Mockito.when(modelMap.containsKey("accountFields")).thenReturn(true);
        Mockito.when(modelMap.addAttribute(Mockito.eq("flash"),  flashArgument.capture())).thenReturn(modelMap);
        
        //merchantDap
        Mockito.when(merchantDao.findCurrentMerchant()).thenReturn(merchant);
        
        //accountForm
        Mockito.when(accountForm.validate(merchantDao, bindingResult)).thenReturn(true);
        Mockito.when(accountForm.getPassword()).thenReturn("");
    }
    
    @Test
    public void testAccountView(){
        Assert.assertEquals("/dashboard/default/account", controller.account(modelMap));
        Mockito.verify(merchantDao, Mockito.never()).findCurrentMerchant();
        
        Mockito.when(modelMap.containsKey("accountFields")).thenReturn(false);
        Assert.assertEquals("/dashboard/default/account", controller.account(modelMap));
        Mockito.verify(modelMap).addAttribute(Mockito.eq("accountFields"), Mockito.any(AccountForm.class));
        Mockito.verify(merchantDao).findCurrentMerchant();
    }
    
    @Test
    public void testUpdateAccount(){
        Assert.assertEquals("/dashboard/default/account", controller.updateAccount(accountForm, bindingResult, modelMap, httpServletRequest));
        Mockito.verify(accountForm).validate(merchantDao, bindingResult);
        Mockito.verify(merchantDao).findCurrentMerchant();
        Mockito.verify(accountForm).setAttributes(merchant);
        Mockito.verify(merchant, Mockito.never()).setPassword(Mockito.anyString());
        Mockito.verify(merchantDao).update(merchant);
        Mockito.verify(modelMap).addAttribute("accountFields", accountForm);
        Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
        
        Mockito.when(accountForm.getPassword()).thenReturn("username");
        Mockito.when(merchant.getSalt()).thenReturn("1234");
        Assert.assertEquals("/dashboard/default/account", controller.updateAccount(accountForm, bindingResult, modelMap, httpServletRequest));
        Mockito.verify(merchant).setNewPassword("username");
        
        Mockito.when(accountForm.validate(merchantDao, bindingResult)).thenReturn(false);
        Assert.assertEquals("/dashboard/default/account", controller.updateAccount(accountForm, bindingResult, modelMap, httpServletRequest));
        Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
    }
}
