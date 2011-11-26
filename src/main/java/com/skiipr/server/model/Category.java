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
@Table(name = "tbl_category")
public class Category implements Serializable {
    
    @Id
    private Long categoryId;
    private String name;
    private Long merchantId;
    
    public void setCategoryID(Long id){
        categoryId = id;
    }
    
    public void setName(String categoryName){
        name = categoryName;
    }
    
    public void setMerchantId(Long id){
        merchantId = id;
    }
    
    public Long getCategoryID(){
        return categoryId;
    }
    
    public String getName(){
        return name;
    }
    
    public Long getMerchantId(){
        return merchantId;
    }
    
    
    
    
    
}
