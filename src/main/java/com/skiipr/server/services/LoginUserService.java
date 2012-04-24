/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.services;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("LoginUserService") 
public class LoginUserService implements UserDetailsService {

    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired 
    private MerchantUserAssembler assembler;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Merchant merchant = merchantDao.findByUsername(username);
        if(merchant == null || merchant.isLocked()){
            throw new UsernameNotFoundException("user not found");
        }
        return assembler.buildUserFromUserEntity(merchant);
    }

 

}