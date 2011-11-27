/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "tbl_product")
public class Product implements Serializable {
    
    @Id
    private Long productID;
    private String description;
    private float price;
    private boolean active;
    private Long categoryID;
    
    public void setProductID(Long id){
        this.productID = id;
        
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setPrice(float price){
        this.price = price;
    }
    
    public void setActive(boolean active){
        this.active = active;
    }
    
    public void  setCategoryID(Long id){
        this.categoryID = id;
    }
    
    public Long getProductID(){
        return productID;
        
    }
    
    public String getDescription(){
        return description;
    }
    
    public float getPrice(){
        return price;
    }
    
    public boolean getActive(){
        return active;
    }
    
    public Long getCategoryID(){
        return categoryID;
    }
    
    
    
    
}
