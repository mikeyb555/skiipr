package com.skiipr.server.components;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>{

    @Autowired
    private MerchantDao merchantDao;
    
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        Merchant merchant = merchantDao.findByUsername(username);
        if(merchant != null){
            merchant.noteLoginFailure();
            merchantDao.update(merchant);
        }
    }
    
}
