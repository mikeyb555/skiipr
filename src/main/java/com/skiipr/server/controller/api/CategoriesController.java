/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;

import com.skiipr.server.httpresponses.NotFound;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Michael
 */

@Controller
public class CategoriesController {
    @Autowired
    CategoryDao categoryDao;
    
    @RequestMapping(value="/api/categories/find/{id}", method = RequestMethod.GET)
    public @ResponseBody Category findCategory(@PathVariable Long id){
        Category category = categoryDao.findByID(id);
        if(category == null){
            throw new NotFound();
        }
        return category;
    }
    
    @RequestMapping(value="/api/categories/all", method = RequestMethod.GET)
    public @ResponseBody List<Category> findAll(){
        List<Category> categories = categoryDao.findAll();
        return categories;
    }  
    
    
    @RequestMapping(value="/api/categories/merchant/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Category> findByMerchant(@PathVariable Long id){
        List<Category> category = categoryDao.findByMerchantId(id);
        if(category == null){
            throw new NotFound();
        }
        return category;
    }
    
    

    
}
