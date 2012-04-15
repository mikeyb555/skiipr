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
import com.skiipr.server.model.Plan;
import com.skiipr.server.model.form.RegisterForm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private PlanDao planDao;
    
    private final Long defaultPlan = 2l;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        System.out.println("Index controller ran");
        return "/website/index/index";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap modelMap){
        
        if(!modelMap.containsKey("registerForm")){
            RegisterForm fieldModel = new RegisterForm();
            modelMap.addAttribute("registerForm", fieldModel);
        }
        
        List<Country> countries = new ArrayList<Country>(Arrays.asList(Country.values()));
        modelMap.addAttribute("countries", countries);
        return "/website/register/index";
        
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createMerchant(RegisterForm registerForm, BindingResult bindingResult, 
        HttpServletRequest req, ModelMap modelMap,
        @RequestParam("recaptcha_challenge_field") String challenge, 
        @RequestParam("recaptcha_response_field") String response){
        registerForm.setCaptchaChallenge(challenge);
        registerForm.setCaptchaResponse(response);
        if(!registerForm.validate(merchantDao, bindingResult, req)){
           modelMap.addAttribute("flash", 
                   FlashNotification.create(Status.FAILURE, 
                   "There was an error with your registration"));
           return register(modelMap);
        } else{
            Plan plan = planDao.findByID(defaultPlan);
            Merchant merchant = registerForm.generateMerchant(plan);
            merchantDao.save(merchant);
            merchant.sendActivationEmail();
        }
        return "/website/register/success";
    }
    
    @RequestMapping(value = "/activate/{id}/{code}", method = RequestMethod.GET)
    public String activate(@PathVariable("id") Long id, 
        @PathVariable("code") String code, Model model){
        Merchant merchant = merchantDao.findById(id);
        if(merchant == null){
            model.addAttribute("message", "merchant.code.activation.failed");
        }else if(!merchant.isLocked()){
            model.addAttribute("message", "merchant.code.activation.already");
        }else if(merchant.activateEmail(code)){
            model.addAttribute("message", "merchant.code.activation.succeed");
            merchantDao.update(merchant);
        }else{
            model.addAttribute("message", "merchant.code.activation.failed");
        }
        return "/website/register/activate";
    }

}
