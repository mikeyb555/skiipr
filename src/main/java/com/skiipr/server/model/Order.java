/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "tbl_order")
public class Order implements Serializable {
    @Id
    private Long orderID;
    private Long orderTime;
    private Merchant merchant;
    private String paypalAddress;
    private Long paypalRef;
    private Long orderType;
    private Long status;
    private Long lastUpdated;
    private float total;
    private Set<Product> products = new HashSet<Product>(0);
    
    
    public void setOrderID(Long id){
        this.orderID = id;
    }
    
    public void setOrderTime(Long time){
        this.orderTime = time;
    }
    
    public void setPaypalAddress(String description){
        this.paypalAddress = description ;
    }
    
    public void setPaypalRef(Long ref){
        this.paypalRef = ref;
    }
    
    public void setOrderType(Long type){
        this.orderType = type;
        
    }
    
    public void setStatus(Long status){
        this.status = status;
    }
    
    public void setLastUpdated(Long update){
        this.lastUpdated = update;
    }
    
    public void setTotal(float total){
        this.total = total;
    }
    
    public Long getOrderID(){
        return orderID;
    }
    
    public Long getOrderTime(){
        return orderTime;
    }
    
    public Long getMerchantID(){
        return merchant.getMerchantID();
    }
    
    public Merchant getMerchant(){
        return merchant;
    }
    
    public void setMerchant(Merchant merchant){
        this.merchant = merchant;
    }
    
    public String getPaypalAddress(){
        return paypalAddress;
    }
    
    public Long getPaypalRef(){
        return paypalRef;
    }
    
    public Long getOrderType(){
        return orderType;
        
    }
    
    public Long getStatus(){
        return status;
    }
    
    public Long getLastUpdated(){
        return lastUpdated;
    }
    
    public float getTotal(){
        return total;
    }

    /**
     * @return the products
     */
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
