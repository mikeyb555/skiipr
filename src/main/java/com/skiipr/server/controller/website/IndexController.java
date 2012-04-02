/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.website;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.enums.Country;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.form.RegisterForm;
import com.skiipr.server.services.EmailService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String register(ModelMap modelMap){
        System.out.println("Register ran controller ran");
        List<Country> countries = new ArrayList<Country>(Arrays.asList(Country.values()));
        modelMap.addAttribute("countries", countries);
        return "/website/register/index";
        
    }
    @RequestMapping(value = "/register", method = RequestMethod.PUT)
    public String createMerchant(RegisterForm registerForm, BindingResult bindingResult, HttpServletRequest req, ModelMap modelMap,@RequestParam("recaptcha_challenge_field") String challenge, @RequestParam("recaptcha_response_field") String response){
        String remoteAddr = req.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6Leic88SAAAAANP_e0IxARCNBj0my4NfR-oHApOD");
        ReCaptchaResponse reCaptchaResponse =
        reCaptcha.checkAnswer(remoteAddr, challenge, response);
        System.out.println(reCaptchaResponse.isValid());
        registerForm.setCaptcha(reCaptchaResponse.isValid());
        
        if(!registerForm.validate(merchantDao, bindingResult)){
           modelMap.addAttribute("flash", 
                   FlashNotification.create(Status.FAILURE, 
                   "There was an error with your registration"));
           registerForm.setModel(modelMap);
           return register(modelMap);
        } else{
            Merchant merchant = new Merchant();
            registerForm.setAttributes(merchant);
            System.out.println("the username is: " + registerForm.getUsername());
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
        LinkedList<String> recipients = new LinkedList<String>();
        recipients.add(registerForm.getEmail());
        EmailService emailService = new EmailService();
        emailService.SendMail("noreply@skiipr.com", recipients, "Skiipr Activation Email ACTION REQUIRED", "Yeah Sup welcome to skiipr, check it out");
        return "/website/register/success";
    }
      
    
    
}
