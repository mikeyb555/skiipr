package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.LatLongGenerator;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.CurrencyType;
import com.skiipr.server.enums.MerchantType;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Plan;
import com.skiipr.server.model.validators.MerchantValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {
    
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private PlanDao planDao;
    
    @Autowired
    private SessionUser sessionUser;
    
    @Autowired
    private MerchantValidator merchantValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("username", "password", "salt", "MerchantId");
        binder.setValidator(merchantValidator);
    }
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.POST)
    public String updateAccountDetails(@Valid Merchant merchant, BindingResult bindingResult, ModelMap model, HttpServletRequest httpServletRequest){
        if (bindingResult.hasErrors()) {
            model.addAttribute("merchant", merchant);
            System.out.println("Error validating");
            return acctountDetails(model);
        }
        LoginUser user = sessionUser.getUser();
        merchant.setMerchantID(user.getMerchantId());
        merchantDao.update(merchant);
        return "redirect:/dashboard/account";
    }
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.GET)
    public String acctountDetails(ModelMap model){
        if(!model.containsKey("merchant")){
            LoginUser user = sessionUser.getUser();
            Merchant merchant = merchantDao.findById(user.getMerchantId());
            model.addAttribute("merchant", merchant);
        }
        List<Plan> plans = planDao.findAll();
        model.addAttribute("plans", plans);
        List<MerchantType> merchantTypes = new ArrayList<MerchantType>(Arrays.asList(MerchantType.values()));
        List<CurrencyType> currencyTypes = new ArrayList<CurrencyType>(Arrays.asList(CurrencyType.values()));
        model.addAttribute("merchantTypes", merchantTypes);
        model.addAttribute("currencyTypes", currencyTypes);
        return "/dashboard/default/account";
    }
}