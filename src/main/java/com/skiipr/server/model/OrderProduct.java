/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "tbl_orderproduct")
public class OrderProduct {
    @Id
    private Long orderProductID;
    private Product product;
    @ManyToOne
    private Order userOrder;
    private int quantity;

    /**
     * @return the orderProductID
     */
    public Long getOrderProductID() {
        return orderProductID;
    }

    /**
     * @param orderProductID the orderProductID to set
     */
    public void setOrderProductID(Long orderProductID) {
        this.orderProductID = orderProductID;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the order
     */
    public Order getUserOrder() {
        return userOrder;
    }

    /**
     * @param order the order to set
     */
    public void setUserOrder(Order userOrder) {
        this.userOrder = userOrder;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
    
}
