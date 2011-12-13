package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private SessionUser sessionUser;
    
    @RequestMapping(value = "/dashboard/categories/view/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("category", categoryDao.findByID(id));
        uiModel.addAttribute("itemId", id);
        LoginUser user = sessionUser.getUser();
        
        if (categoryDao.findByID(id).getMerchantID() != user.getMerchantId()){
            return "redirect:/dashboard/categories";
        }else{
        return "/dashboard/categories/view";
        }
    }
    
   @RequestMapping(value = "/dashboard/categories", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
       LoginUser user = sessionUser.getUser();
        if (page != null || size != null) {
            model.addAttribute("categories", categoryDao.findByMerchantId(user.getMerchantId()));
        } else {
            model.addAttribute("categories", categoryDao.findByMerchantId(user.getMerchantId()));
        }
        model.addAttribute("categories", categoryDao.findByMerchantId(user.getMerchantId()));
        return "/dashboard/categories/list";
    }
   
   @RequestMapping(value = "/dashboard/categories/edit", method = RequestMethod.PUT)
    public String update(@Valid Category category, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "/dashboard/categories/edit";
        }
        uiModel.asMap().clear();
        categoryDao.update(category);
        
        return "redirect:/dashboard/categories/edit/" + category.getCategoryID().toString();
        
       
    }
    
    @RequestMapping(value = "/dashboard/categories/edit/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        LoginUser user = sessionUser.getUser();
        
        if (categoryDao.findByID(id).getMerchantID() != user.getMerchantId()){
            return "redirect:/dashboard/categories";
        }else{
        uiModel.addAttribute("category", categoryDao.findByID(id));
        return "/dashboard/categories/update";
        }
    }
    
    @RequestMapping(value = "/dashboard/categories/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Category category = categoryDao.findByID(id);
        categoryDao.delete(category);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect://dashboard/categories";
    }
    
    @RequestMapping(value = "/dashboard/categories/new", method = RequestMethod.POST)
    public String create(@Valid Category category, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "/dashboard/categories/create";
        }
        uiModel.asMap().clear();
        LoginUser user = sessionUser.getUser();
        category.setMerchantID(user.getMerchantId());
        categoryDao.save(category);
        return "redirect:/dashboard/categories/view/" + category.getCategoryID().toString();
    }
    
    @RequestMapping(value = "/dashboard/categories/new", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("category", new Category());
        return "/dashboard/categories/create";
    }
}
