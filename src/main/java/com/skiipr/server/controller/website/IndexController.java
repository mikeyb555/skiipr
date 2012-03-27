/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.website;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.form.RegisterForm;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private PlanDao planDao;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        System.out.println("Index controller ran");
        return "/website/index/index";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        System.out.println("Register ran controller ran");
        return "/website/register/index";
        
    }
    @RequestMapping(value = "/register", method = RequestMethod.PUT)
    public String createMerchant(RegisterForm formRegister, BindingResult bindingResult, ModelMap modelMap){
        if(!formRegister.validate()){
            return "website/register/fail";
        } else{
            Merchant merchant = new Merchant();
            formRegister.setAttributes(merchant);
            System.out.println("the username is: " + formRegister.getUsername());
            merchant.setCodEnabled(Boolean.TRUE);
            merchant.setConsoleSoundEnabled(true);
            merchant.setCurrencyType("USD");
            merchant.setFailedLoginTime(0);
            merchant.setLoginCount(0);
            merchant.setLastChange(0l);
            merchant.setLastLoginTime(0);
            merchant.setLatitude("0");
            merchant.setLongitude("0");
            merchant.setOpen(Boolean.TRUE);
            merchant.setSalt("1234");
            merchant.setType(1);
            merchant.setPlan(planDao.findByID(3l));
            merchant.setPaypalEnabled(Boolean.TRUE);
            merchantDao.save(merchant);
            
        }
        return "/website/register/success";
    }
      
    
    
}
