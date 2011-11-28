/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Product;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author timbell
 */
@Controller
public class ProductController {
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    @RequestMapping(value = "/dashboard/products/edit/{id}", method = RequestMethod.POST)
    public String index(Merchant merchant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){

        return "redirect:/dashboard/account";
    }
    
    @RequestMapping(value = "/dashboard/products/view/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("product", productDao.findByID(id));
        uiModel.addAttribute("itemId", id);
        Category category = categoryDao.findByID(productDao.findByID(id).getCategoryID());
        uiModel.addAttribute("category", category);
        return "/dashboard/products/view";
    }
    
    @RequestMapping(value = "/dashboard/products", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            model.addAttribute("products", productDao.findAll());
        } else {
            model.addAttribute("products", productDao.findAll());
        }
        model.addAttribute("products", productDao.findAll());
        return "/dashboard/products/list";
    }
    
    @RequestMapping(value = "/dashboard/products/edit", method = RequestMethod.PUT)
    public String update(@Valid Product product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("product", product);
            return "/dashboard/products/edit";
        }
        uiModel.asMap().clear();
        productDao.update(product);
        return "redirect:/dashboard/products/edit/" + product.getCategoryID().toString();
    }
    
    @RequestMapping(value = "/dashboard/products/edit/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("product", productDao.findByID(id));
        return "/dashboard/products/update";
    }
    
    @RequestMapping(value = "/dashboard/products/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Product product = productDao.findByID(id);
        productDao.delete(product);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect://dashboard/products";
    }
    
    @RequestMapping(value = "/dashboard/products/new", method = RequestMethod.POST)
    public String create(@Valid Product product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("product", product);
            return "/dashboard/products/create";
        }
        uiModel.asMap().clear();
        
        
        
    
        productDao.save(product);
        return "redirect:/dashboard/products/view/" + product.getProductID().toString();
    }
    
    @RequestMapping(value = "/dashboard/products/new", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("product", new Product());
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Category> categories = categoryDao.findByMerchantId(user.getMerchantId());
        uiModel.addAttribute("categories", categories);
        return "/dashboard/products/create";
    }
    
    
    
    
    
    
}
