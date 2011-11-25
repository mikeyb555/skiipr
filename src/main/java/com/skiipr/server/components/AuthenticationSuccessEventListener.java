package com.skiipr.server.components;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>{

    @Autowired
    private MerchantDao merchantDao;
    
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        Merchant merchant = merchantDao.findByUsername(username);
        if(merchant != null){
            merchant.noteLoginSuccess();
            merchantDao.update(merchant);
        }
    }
    
}
