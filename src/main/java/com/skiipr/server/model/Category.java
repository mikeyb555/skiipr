/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "tbl_category")
public class Category implements Serializable {
    
    @Id
    private Long categoryID;
    @NotBlank
    @Length(max= 128)
    private String name;
    private Long merchantId;
    private List<Product> products;
    
    
    public void setCategoryID(Long id){
        categoryID = id;
    }
    
    public void setName(String categoryName){
        name = categoryName;
    }
    
    public void setMerchantID(Long id){
        merchantId = id;
    }
    
    public Long getCategoryID(){
        return categoryID;
    }
    
    public String getName(){
        return name;
    }
    
    @JsonIgnore
    public Long getMerchantID(){
        return merchantId;
    }
    
    public void setProducts(List<Product> products){
        this.products = products;
        
    }
    
    @JsonIgnore
    public List<Product> getProducts(){
        return products;
    }
    
}
