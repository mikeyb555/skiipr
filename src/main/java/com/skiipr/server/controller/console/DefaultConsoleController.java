package com.skiipr.server.controller.console;

import com.skiipr.server.model.DAO.BannedDao;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultConsoleController {
    
    @Autowired
    MerchantDao merchantDao;
    
    @Autowired
    BannedDao bannedDao;
    
    @RequestMapping(value="/console", method = RequestMethod.GET)
    public String index(ModelMap modelMap){
        Merchant merchant = merchantDao.findCurrentMerchant();
        modelMap.addAttribute("consoleSettings", merchant.getConsoleOptions());
        String sound;
        if(merchant.isConsoleSoundEnabled()){
            sound = "on";
        }else{
            sound= "off";
        }
        modelMap.addAttribute("console_sound_value", sound);
        return "/dashboard/console/index";
    }
    
}
