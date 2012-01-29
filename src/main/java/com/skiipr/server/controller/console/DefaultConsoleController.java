package com.skiipr.server.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultConsoleController {
    
    @RequestMapping(value="/console", method = RequestMethod.GET)
    public String index(ModelMap modelMap){
        return "/dashboard/console/index";
    }
}
