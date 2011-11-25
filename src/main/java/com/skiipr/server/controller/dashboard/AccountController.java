package com.skiipr.server.controller.dashboard;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Plan;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("username", "password", "salt", "MerchantId");
    }
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.POST)
    public String index(Merchant merchant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        merchant.setMerchantID(user.getMerchantId());
        merchantDao.update(merchant);
        return "redirect:/dashboard/account";
    }
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.GET)
    public String index(ModelMap model){
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Merchant merchant = merchantDao.findById(user.getMerchantId());
        model.addAttribute("merchant", merchant);
        List<Plan> plans = planDao.findAll();
        model.addAttribute("plans", plans);
        return "/dashboard/default/account";
    }
}