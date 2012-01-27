package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Country;
import com.skiipr.server.enums.CurrencyType;
import com.skiipr.server.enums.MerchantType;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.Banned;
import com.skiipr.server.model.DAO.BannedDao;
import com.skiipr.server.model.DAO.CouponDao;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.form.SettingsForm;
import java.util.List;
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
public class SettingsControllerTest {

        @Mock
        MerchantDao merchantDao;
        
        @Mock
        PlanDao planDao;
        
        @Mock
        SessionUser sessionUser;
        
        @Mock
        CouponDao couponDao;
        
        @Mock
        BannedDao bannedDao;
        
        @Mock
        ModelMap modelMap;
        
        @Mock
        Merchant merchant;
        
        @Mock
        BindingResult bindingResult;
        
        @Mock
        HttpServletRequest httpServletRequest;
        
        @Mock
        SettingsForm settingsForm;
        
        @Mock
        List<Banned> bannedList;
        
        @Mock
        Banned banned;
        
        @Mock
        LoginUser loginUser;
        
        @InjectMocks
        private SettingsController controller;
        
        private ArgumentCaptor<FlashNotification> flashArgument;
        
        @Before
        public void setUpClass() throws Exception {
            controller = new SettingsController();
            MockitoAnnotations.initMocks(this);
            
            flashArgument = ArgumentCaptor.forClass(FlashNotification.class);
            
            //modelMap
            Mockito.when(modelMap.containsKey("merchantModel")).thenReturn(true);
            Mockito.when(modelMap.addAttribute(Mockito.eq("flash"),  flashArgument.capture())).thenReturn(modelMap);
            
            //merchantDao
            Mockito.when(merchantDao.findCurrentMerchant()).thenReturn(merchant);
            
            //settingsForm
            Mockito.when(settingsForm.validate(bindingResult, merchantDao)).thenReturn(true);
            
            //bannedDao
            Mockito.when(bannedDao.findAll()).thenReturn(bannedList);
            Mockito.when(bannedDao.getBan(5l)).thenReturn(banned);
            Mockito.when(bannedDao.isBanned("test@test.com")).thenReturn(false);
            Mockito.when(bannedDao.isBanned("not_an_email")).thenReturn(false);
            
            //sessionUser
            Mockito.when(sessionUser.getUser()).thenReturn(loginUser);
        }
        
        @Test
        public void testViewSettings(){
            Assert.assertEquals("/dashboard/settings", controller.viewSettings(modelMap));
            Mockito.verify(modelMap).addAttribute(Mockito.eq("merchantTypes"), Mockito.anyCollectionOf(MerchantType.class));
            Mockito.verify(modelMap).addAttribute(Mockito.eq("currencyTypes"), Mockito.anyCollectionOf(CurrencyType.class));
            Mockito.verify(modelMap).addAttribute(Mockito.eq("countries"), Mockito.anyCollectionOf(Country.class));
            
            Mockito.when(modelMap.containsKey("merchantModel")).thenReturn(false);
            Assert.assertEquals("/dashboard/settings", controller.viewSettings(modelMap));
            Mockito.verify(merchantDao).findCurrentMerchant();
            Mockito.verify(modelMap).addAttribute(Mockito.eq("merchantModel"), Mockito.any(SettingsForm.class));
        }
        
        @Test
        public void testUpdateSettings(){
            Assert.assertEquals("/dashboard/settings", controller.updateSettings(settingsForm, bindingResult, modelMap, httpServletRequest));
            Mockito.verify(settingsForm).validate(bindingResult, merchantDao);
            Mockito.verify(merchantDao).findCurrentMerchant();
            Mockito.verify(merchantDao).update(merchant);
            Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
            Mockito.verify(modelMap).addAttribute("merchantModel", settingsForm);
            
            Mockito.when(settingsForm.validate(bindingResult, merchantDao)).thenReturn(false);
            Assert.assertEquals("/dashboard/settings", controller.updateSettings(settingsForm, bindingResult, modelMap, httpServletRequest));
            Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
        }
        
        @Test
        public void testViewSecurity(){
            Assert.assertEquals("/dashboard/settings/security", controller.viewSecurity(modelMap));
            Mockito.verify(bannedDao).findAll();
            Mockito.verify(modelMap).addAttribute("banned", bannedList);
        }
        
        @Test
        public void testDeleteBan(){
            Assert.assertEquals("/dashboard/settings/security", controller.deleteBan(5l, modelMap, httpServletRequest));
            Mockito.verify(bannedDao).getBan(5l);
            Mockito.verify(bannedDao).delete(banned);
            Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
            
            Mockito.when(bannedDao.getBan(5l)).thenReturn(null);
            Assert.assertEquals("/dashboard/settings/security", controller.deleteBan(5l, modelMap, httpServletRequest));
            Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
        }
        
        @Test
        public void testAddBan(){
            Assert.assertEquals("/dashboard/settings/security", controller.addBan("test@test.com", modelMap, httpServletRequest));
            Mockito.verify(bannedDao).save(Mockito.any(Banned.class));
            Assert.assertEquals(Status.SUCCESS, flashArgument.getValue().getStatus());
            
            Assert.assertEquals("/dashboard/settings/security", controller.addBan("not_an_email", modelMap, httpServletRequest));
            Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
            
            Mockito.when(bannedDao.isBanned("test@test.com")).thenReturn(true);
            Assert.assertEquals(Status.FAILURE, flashArgument.getValue().getStatus());
        }
}
