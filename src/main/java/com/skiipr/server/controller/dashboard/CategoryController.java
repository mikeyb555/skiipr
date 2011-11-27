package com.skiipr.server.controller.dashboard;

import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.Merchant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    
    @RequestMapping(value = "/dashboard/categories/edit/{id}", method = RequestMethod.POST)
    public String index(Merchant merchant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){

        return "redirect:/dashboard/account";
    }
    
    @RequestMapping(value = "/dashboard/categories", method = RequestMethod.GET)
    public String index(ModelMap model){
        model.addAttribute("categories", categoryDao.findAll());
        return "/dashboard/categories/list";
    }
}
