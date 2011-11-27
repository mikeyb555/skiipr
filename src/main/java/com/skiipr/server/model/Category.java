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
    private Long categoryID;
    private String name;
    private Long merchantId;
    
    public void setCategoryID(Long id){
        categoryID = id;
    }
    
    public void setName(String categoryName){
        name = categoryName;
    }
    
    public void setMerchantID(Long id){
        merchantId = id;
    }
    
    @Id
    public Long getCategoryID(){
        return categoryID;
    }
    
    public String getName(){
        return name;
    }
    
    public Long getMerchantID(){
        return merchantId;
    }      
    
}
