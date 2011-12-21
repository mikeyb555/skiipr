package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Product;
import java.util.List;
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
public class ProductController {
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private SessionUser sessionUser;
    
    @RequestMapping(value = "/dashboard/products/view/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("product", productDao.findByMerchant(id));
        uiModel.addAttribute("itemId", id);
        return "/dashboard/products/view";
    }
    
   @RequestMapping(value = "/dashboard/products", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if(size == null){
            size = 10;
        }
        if(page == null){
            page = 1;
        }
        Integer sizeNo = this.getPageSize(size);
        Integer startPage = this.getStartPage(page, sizeNo);
        model.addAttribute("products", productDao.findRange(startPage, sizeNo));
        model.addAttribute("maxPages", this.getMaxPages(sizeNo));
        return "/dashboard/products/list";
    }
    
    @RequestMapping(value = "/dashboard/products/edit", method = RequestMethod.PUT)
    public String update(@Valid Product product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("product", product);
            uiModel.addAttribute("categories", categoryDao.findByMerchantId());
            return updateForm(product.getProductID(), uiModel);
        }
        uiModel.asMap().clear();
        uiModel.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Product Updated"));
        productDao.update(product);
        return updateForm(product.getProductID(), uiModel);
    }
    
    @RequestMapping(value = "/dashboard/products/edit/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Product product = productDao.findByMerchant(id);
        if (product == null){
            return "redirect:/dashboard/products";
        }else{
            uiModel.addAttribute("product", product);
            uiModel.addAttribute("categories", categoryDao.findByMerchantId());
            return "/dashboard/products/update";  
        }
        
    }
    
    @RequestMapping(value = "/dashboard/products/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Product product = productDao.findByMerchant(id);
        productDao.delete(product);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        uiModel.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Product Deleted"));
        return list(page, size, uiModel);
    }
    
    @RequestMapping(value = "/dashboard/products/new", method = RequestMethod.POST)
    public String create(@Valid Product product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("product", product);
            uiModel.addAttribute("categories", categoryDao.findByMerchantId());
            return "/dashboard/products/create";
        }
        uiModel.asMap().clear();
        productDao.save(product);
        uiModel.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Product Added"));
        return show(product.getProductID(), uiModel);
        
        
    }
    
    @RequestMapping(value = "/dashboard/products/new", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("product", new Product());
        List<Category> categories = categoryDao.findByMerchantId();
        uiModel.addAttribute("categories", categories);
        return "/dashboard/products/create";
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
        float nrOfPages = (float) productDao.countByMerchant() / sizeNo;
        if(nrOfPages > (int) nrOfPages || nrOfPages == 0.0){
            return (int) nrOfPages + 1;
        }else{
            return (int) nrOfPages;
        }
    }
    
    
    
    
    
    
}
