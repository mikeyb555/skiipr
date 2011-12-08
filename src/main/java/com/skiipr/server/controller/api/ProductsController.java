/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;

import com.skiipr.server.httpresponses.NotFound;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Product;
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
public class ProductsController {
    
    @Autowired
    ProductDao productDao;
    
    @RequestMapping(value="/api/products/find/{id}", method = RequestMethod.GET)
    public @ResponseBody Product findProduct(@PathVariable Long id){
        Product product = productDao.findByID(id);
        if(product == null){
            throw new NotFound();
        }
        return product;
    }
    
    @RequestMapping(value="/api/products/category/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Product> findByCategory(@PathVariable Long id){
        List<Product> product = productDao.findByCategoryID(id);
        if(product == null){
            throw new NotFound();
        }
        return product;
    }
    
    
    
    @RequestMapping(value="/api/products/all", method = RequestMethod.GET)
    public @ResponseBody List<Product> findAll(){
        List<Product> products = productDao.findAll();
        return products;
    }  
    
    
    
}
