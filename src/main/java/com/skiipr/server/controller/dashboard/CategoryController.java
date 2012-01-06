package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.LoginUser;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private ProductDao productDao;
    
    @Autowired
    private SessionUser sessionUser;
    
    @RequestMapping(value = "/dashboard/categories/view/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("category", categoryDao.findCategoryByMerchantId(id));
        uiModel.addAttribute("itemId", id);
        return "/dashboard/categories/view";
        
    }
    
   @RequestMapping(value = "/dashboard/categories", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if(size == null){
            size = 10;
        }
        if(page == null){
            page = 1;
        }
        Integer sizeNo = this.getPageSize(size);
        Integer startPage = this.getStartPage(page, sizeNo);
        model.addAttribute("categories", categoryDao.findRange(startPage, sizeNo));
        model.addAttribute("maxPages", this.getMaxPages(sizeNo));
        return "/dashboard/categories/list";
    }
   
   @RequestMapping(value = "/dashboard/categories/edit", method = RequestMethod.PUT)
    public String update(@Valid Category category, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "/dashboard/categories/edit";
        }
        uiModel.asMap().clear();
        uiModel.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Category Updated"));
        categoryDao.update(category);
        
        return updateForm(category.getCategoryID(), uiModel);
        
       
    }
    
    @RequestMapping(value = "/dashboard/categories/edit/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("category", categoryDao.findCategoryByMerchantId(id));
        return "/dashboard/categories/update";
        }
    
    
    @RequestMapping(value = "/dashboard/categories/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Category category = categoryDao.findCategoryByMerchantId(id);
        if(!productDao.findByCategoryID(category.getCategoryID()).isEmpty()){
            uiModel.asMap().clear();
            uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
            uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
            uiModel.addAttribute("flash", FlashNotification.create(Status.FAILURE, "Category Has products associated with it. "
                    + "Please delete or change the category of these products before deleting the category"));
            return list(page, size, uiModel);
        }else{
            categoryDao.delete(category);
            uiModel.asMap().clear();
            uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
            uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
            uiModel.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Category Deleted"));
            return list(page, size, uiModel);
        }
        
    }
    
    @RequestMapping(value = "/dashboard/categories/view", method = RequestMethod.POST)
    public String create(@Valid Category category, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "/dashboard/categories/create";
        }
        uiModel.asMap().clear();
        
        LoginUser user = sessionUser.getUser();
        category.setMerchantID(user.getMerchantId());
        categoryDao.save(category);
        uiModel.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Category Added"));
        return show(category.getCategoryID(), uiModel);
        //return "redirect:/dashboard/categories/view/" + category.getCategoryID().toString();
    }
    
    @RequestMapping(value = "/dashboard/categories/new", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("category", new Category());
        return "/dashboard/categories/create";
    }
    
    private Integer getPageSize(Integer size){
        if(size == null){
            return 10;
        }else{
            return size;
        }
    }
    
    private Integer getStartPage(Integer page, Integer pageSize){
        if(page == null){
            return 0;
        }else{
            return (page - 1) * pageSize;
        }
    }
    
    private Integer getMaxPages(Integer sizeNo){
        float nrOfPages = (float) categoryDao.countByMerchant() / sizeNo;
        if(nrOfPages > (int) nrOfPages || nrOfPages == 0.0){
            return (int) nrOfPages + 1;
        }else{
            return (int) nrOfPages;
        }
    }
}
