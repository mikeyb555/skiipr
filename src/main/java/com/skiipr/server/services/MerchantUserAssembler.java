/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.services;

import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;

@Service("MerchantUserAssembler")
public class MerchantUserAssembler {

  LoginUser buildUserFromUserEntity(Merchant merchant) {

    String username = merchant.getUsername();
    String password = merchant.getPassword();
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    
    int unixTime = (int) (System.currentTimeMillis() / 1000L);
    int timeDiff = unixTime - merchant.getFailedLoginTime();
    boolean longEnough = 900 < timeDiff;
    boolean notLocked = merchant.getLoginCount() <= 3;
    boolean accountNonLocked = longEnough || notLocked;

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new GrantedAuthorityImpl("dashboard_user"));
    authorities.add(new GrantedAuthorityImpl("console_user"));

    LoginUser user = new LoginUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    user.setSalt(merchant.getSalt());
    user.setMerchantId(merchant.getMerchantID());
    return user;
  }
}
