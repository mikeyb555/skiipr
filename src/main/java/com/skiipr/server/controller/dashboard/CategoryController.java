package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.form.CategoryForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    
   @RequestMapping(value = "/dashboard/categories", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if(size == null){
            size = 10;
        }
        if(page == null){
            page = 1;
        }
        Integer sizeNo = this.getPageSize(size);
        Integer startPage = this.getStartPage(page, sizeNo);
        modelMap.addAttribute("categories", categoryDao.findRange(startPage, sizeNo));
        modelMap.addAttribute("categoryModel", new Category());
        modelMap.addAttribute("maxPages", this.getMaxPages(sizeNo));
        return "/dashboard/categories/list";
    }
   
   @RequestMapping(value = "/dashboard/categories", method = RequestMethod.POST)
    public String update(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, CategoryForm formCategory, BindingResult bindingResult, ModelMap modelMap) {
       if (!formCategory.validate(categoryDao, bindingResult)) {
            modelMap.addAttribute("flash", FlashNotification.create(Status.FAILURE, "There was an error updating your category"));
        }else{
            Category category = categoryDao.findCategoryByMerchantId(formCategory.getCategoryID());
            if(category == null){
                modelMap.addAttribute("flash", FlashNotification.create(Status.FAILURE, "This caregory does not exist."));
            }else{
                formCategory.setAttributes(category);
                categoryDao.update(category);
                modelMap.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Category Updated"));
            }
        }
        modelMap.addAttribute("openCatID", formCategory.getCategoryID());
        return list(page, size, modelMap);
    }
    
    @RequestMapping(value = "/dashboard/categories", method = RequestMethod.DELETE)
    public String deleteCategory(@RequestParam("categoryID") Long id, ModelMap model, HttpServletRequest httpServletRequest){
        Category category = categoryDao.findCategoryByMerchantId(id);
        if(category == null){
           model.addAttribute("flash", FlashNotification.create(Status.FAILURE, "This category does not belong to you or is invalid."));
        }else if(!productDao.findByCategoryID(id).isEmpty()){
            model.addAttribute("flash", FlashNotification.create(Status.FAILURE, "There are still products in this category, please remove them first."));    
        }else{
            categoryDao.delete(category);
            model.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Category deleted"));
        }
        return list(null, null, model);
    }
    
    @RequestMapping(value = "/dashboard/categories", method = RequestMethod.PUT)
    public String create(CategoryForm formCategory, BindingResult bindingResult, ModelMap modelMap) {
       formCategory.setCategoryID(0l);
       if (!formCategory.validate(categoryDao, bindingResult)) {
            modelMap.addAttribute("flash", FlashNotification.create(Status.FAILURE, "There was an error creating your category"));
            modelMap.addAttribute("openCatID", 0);
       }else{
            Category category = new Category();
            formCategory.setAttributes(category);
            category.setMerchantID(sessionUser.getUser().getMerchantId());
            categoryDao.save(category);
            modelMap.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Category Created"));
            modelMap.addAttribute("openCatID", category.getCategoryID());
        }
        return list(null, null, modelMap);
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
