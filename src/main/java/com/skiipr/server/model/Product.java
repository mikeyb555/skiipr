/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbl_product")
public class Product implements Serializable {
    
    @Id
    private Long productID;
    @Length(max = 128)
    private String description;
    @DecimalMin("0")
    private BigDecimal price;
    @NotBlank
    @Length(max= 128)
    private String name;
    private boolean active;
    private Long categoryID;
    @ManyToOne
    private Category category;
    
    public String getFormattedPrice(){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(price);
        
    }
    
    public void setProductID(Long id){
        this.productID = id;
        
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPrice(BigDecimal price){
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
    
    public BigDecimal getPrice(){
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

    public String getName() {
        return name;
    }
    
}
