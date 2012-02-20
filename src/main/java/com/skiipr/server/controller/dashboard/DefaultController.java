/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class DefaultController {
    
    @Autowired
    private MerchantDao merchantDao;
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String index(ModelMap model){
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", merchantDao.findById(user.getMerchantId()));
        return "/dashboard/default/index";
    }
}