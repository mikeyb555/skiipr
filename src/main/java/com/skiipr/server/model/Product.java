/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

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
    private Category category;
    
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
    
    public void setCategory(Category category){
        this.category = category;
    }
    @JsonIgnore
    public Category getCategory(){
        return category;
    }

    
    public Long getCategoryID(){
        return category.getCategoryID();
    }
    
}
