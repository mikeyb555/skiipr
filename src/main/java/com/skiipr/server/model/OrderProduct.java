package com.skiipr.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tbl_orderproduct")
public class OrderProduct {
    @Id
    private Long orderProductID;
    private Product product;
    @ManyToOne
    private Order userOrder;
    private int quantity;

    public Long getOrderProductID() {
        return orderProductID;
    }

    public void setOrderProductID(Long orderProductID) {
        this.orderProductID = orderProductID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonIgnore
    public Order getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(Order userOrder) {
        this.userOrder = userOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
    
}
