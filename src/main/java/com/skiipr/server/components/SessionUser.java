/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.components;

import com.skiipr.server.model.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionUser {
    public LoginUser getUser(){
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
