/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.form;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

/**
 *
 * @author Michael
 */
public class ProductForm {
    private Long productID;
    private String description;
    private BigDecimal price;
    private String name;
    private boolean active;
    private Long categoryID;
    private String formattedPrice;
    
    
    

    /**
     * @return the productID
     */
    public Long getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(Long productID) {
        this.productID = productID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the category
     */
    
    
    public void setAttributes(Product product){
        product.setActive(active);
        System.out.println(categoryID);
        System.out.println(name);
        
        product.setDescription(description);
        product.setName(name);
        if(!product.toString().isEmpty()){
            product.setPrice(price);
            
        }
        
        
        
        
        
        
    }
    
    public void getAttributes(Product product){
        productID = product.getProductID();
        active = product.getActive();
        categoryID = product.getCategory().getCategoryID();
        description = product.getDescription();
        name = product.getName();
        price = product.getPrice();
        formattedPrice = product.getFormattedPrice();
        
    }

    /**
     * @return the formattedPrice
     */
    public String getFormattedPrice() {
        return formattedPrice;
    }

    /**
     * @param formattedPrice the formattedPrice to set
     */
    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }
    
    public boolean validate(ProductDao productDao, Errors errors ){
        try{
            if (!price.toString().matches("\\d[\\d\\,\\.]+")){
            errors.rejectValue("price", "invalid.product.price.incorrect");
    }
            
        
        return true;
            
        }
        catch(Exception e){
            return false;
        }
        
        
}

    /**
     * @return the categoryID
     */
    public Long getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the categories
     */
    
    
}
