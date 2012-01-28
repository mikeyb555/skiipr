package com.skiipr.server.controller.dashboard;

import com.skiipr.server.components.FlashNotification;
import com.skiipr.server.components.SessionUser;
import com.skiipr.server.enums.Status;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Product;
import com.skiipr.server.model.form.ProductForm;
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
public class ProductController {
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private SessionUser sessionUser;
    
   @RequestMapping(value = "/dashboard/products", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        Integer sizeNo = this.getPageSize(size);
        Integer startPage = this.getStartPage(page, sizeNo);
        modelMap.addAttribute("products", productDao.findRange(startPage, sizeNo));
        modelMap.addAttribute("productModel", new Product());
        modelMap.addAttribute("categories", categoryDao.findByMerchantId());
        modelMap.addAttribute("maxPages", this.getMaxPages(sizeNo));
        return "/dashboard/products/list";
    }
   
   @RequestMapping(value = "/dashboard/products", method = RequestMethod.POST)
    public String update(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ProductForm formProduct, BindingResult bindingResult, ModelMap modelMap) {
       if (!formProduct.validate(productDao, bindingResult)) {
            modelMap.addAttribute("flash", FlashNotification.create(Status.FAILURE, "There was an error updating your product"));
            return list(page, size, modelMap);
        }else{
            Product product = productDao.findByMerchant(formProduct.getProductID());
            if(product == null){
                modelMap.addAttribute("flash", FlashNotification.create(Status.FAILURE, "This product does not exist."));
            }else{
                System.out.println(formProduct.getCategoryID());
                formProduct.setAttributes(product);
                product.setCategory(categoryDao.findCategoryByMerchantId(formProduct.getCategoryID()));
                productDao.update(product);
                
                modelMap.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Product Updated"));
            }
        }
        modelMap.addAttribute("openProdID", formProduct.getProductID());
        return list(page, size, modelMap);
    }
    
    @RequestMapping(value = "/dashboard/products", method = RequestMethod.DELETE)
    public String delete(@RequestParam("productID") Long id, ModelMap model, HttpServletRequest httpServletRequest){
        Product product = productDao.findByMerchant(id);
        if(product == null){
           model.addAttribute("flash", FlashNotification.create(Status.FAILURE, "This product does not belong to you or is invalid."));    
        }else{
            productDao.delete(product);
            model.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Product deleted"));
        }
        return list(null, null, model);
    }
    
    @RequestMapping(value = "/dashboard/products", method = RequestMethod.PUT)
    public String create(ProductForm formProduct, BindingResult bindingResult, ModelMap modelMap) {
       formProduct.setProductID(0l);
       if (!formProduct.validate(productDao, bindingResult)) {
            modelMap.addAttribute("flash", FlashNotification.create(Status.FAILURE, "There was an error creating your product"));
            modelMap.addAttribute("categories", categoryDao.findByMerchantId());
            modelMap.addAttribute("openProdID", 0);
       }else{
            Product product = new Product();
            formProduct.setAttributes(product);
            product.setCategory(categoryDao.findCategoryByMerchantId(formProduct.getCategoryID()));
            
            
            productDao.save(product);
            modelMap.addAttribute("openProdID", product.getProductID());
            modelMap.addAttribute("flash", FlashNotification.create(Status.SUCCESS, "Product Created"));
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
        float nrOfPages = (float) productDao.countByMerchant() / sizeNo;
        if(nrOfPages > (int) nrOfPages || nrOfPages == 0.0){
            return (int) nrOfPages + 1;
        }else{
            return (int) nrOfPages;
        }
    }
}
