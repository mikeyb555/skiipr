package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.form.AccountForm;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {
    
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private SessionUser sessionUser;
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.GET)
    public String account(ModelMap model){
        if(!model.containsKey("accountFields")){
            Merchant merchant = merchantDao.findCurrentMerchant();
            AccountForm fieldModel = new AccountForm(merchant);
            model.addAttribute("accountFields", fieldModel);
        }
        return "/dashboard/default/account";
    }
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.POST)
    public String updateAccount(@ModelAttribute("accountFields") AccountForm accountFields, BindingResult bindingResult, ModelMap model, HttpServletRequest httpServletRequest){
        if(accountFields.validate(merchantDao, bindingResult)){
            Merchant merchant = merchantDao.findCurrentMerchant();
            accountFields.setAttributes(merchant);
            if(!accountFields.getPassword().isEmpty()){
                PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
                String password = passwordEncoder.encodePassword(accountFields.getPassword(), merchant.getSalt());
                merchant.setPassword(password);
            }
            merchantDao.update(merchant);
            model.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Details updated"));
        }else{
            model.addAttribute("flash", FlashNotification.create(Status.FAILURE, "There was an error with your details."));
        }
        model.addAttribute("accountFields", accountFields);
        return account(model);
    }
}
