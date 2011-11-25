package com.skiipr.server.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class LoginUser extends User{
    private Long merchantId;
    private String salt;
    
    public LoginUser(String username, 
            String password, 
            boolean enabled, 
            boolean accountNonExpired, 
            boolean credentialsNonExpired, 
            boolean accountNonLocked, 
            Collection<? extends GrantedAuthority> authorities){
        
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    
    public Long getMerchantId(){
        return merchantId;
    }
    
    public void setMerchantId(long id){
        merchantId = id;
    }
    
    public String getSalt(){
        return salt;
    }
    
    public void setSalt(String salt){
        this.salt = salt;
    }
}
